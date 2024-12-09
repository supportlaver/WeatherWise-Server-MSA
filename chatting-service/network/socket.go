package network

import (
	"chatting-service/service"
	"chatting-service/types"
	"log"
	"net/http"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
)

var upgrader = &websocket.Upgrader{
	ReadBufferSize:  types.SocketBufferSize,
	WriteBufferSize: types.MessageBufferSize,
	CheckOrigin:     func(r *http.Request) bool { return true },
}

type Room struct {
	Forward chan *message
	Join    chan *client
	Leave   chan *client
	Clients map[*client]bool
	mu      sync.Mutex // Clients 보호를 위한 Mutex
	service *service.Service
}

type message struct {
	Name    string    `json:"name"`
	Message string    `json:"message"`
	Room    string    `json:"room"`
	When    time.Time `json:"when"`
}

type client struct {
	Socket *websocket.Conn
	Send   chan *message
	Room   *Room
	Name   string `json:"name"`
}

func NewRoom(service *service.Service) *Room {
	return &Room{
		Forward: make(chan *message),
		Join:    make(chan *client),
		Leave:   make(chan *client),
		Clients: make(map[*client]bool),
		service: service,
	}
}

func (c *client) Read() {
	defer func() {
		c.Socket.Close()
		c.Room.Leave <- c
	}()

	for {
		var msg *message
		err := c.Socket.ReadJSON(&msg)
		if err != nil {
			log.Println("Error reading JSON:", err)
			break
		}

		msg.When = time.Now().Local()
		msg.Name = c.Name
		c.Room.Forward <- msg
	}
}

func (c *client) Write() {
	defer func() {
		if r := recover(); r != nil {
			log.Println("Recovered in Write:", r)
		}
		c.Socket.Close()
	}()

	for msg := range c.Send {
		err := c.Socket.WriteJSON(msg)
		if err != nil {
			log.Println("Error writing JSON:", err)
			break
		}
	}
}

func (r *Room) Run() {
	for {
		select {
		case client := <-r.Join:
			r.mu.Lock()
			r.Clients[client] = true
			r.mu.Unlock()
		case client := <-r.Leave:
			r.mu.Lock()
			if _, ok := r.Clients[client]; ok {
				close(client.Send)
				delete(r.Clients, client)
			}
			r.mu.Unlock()
		case msg := <-r.Forward:
			go r.service.InsertChatting(msg.Name, msg.Message, msg.Room)
			r.mu.Lock()
			for client := range r.Clients {
				select {
				case client.Send <- msg:
				default:
					log.Println("Message dropped for client:", client.Name)
				}
			}
			r.mu.Unlock()
		}
	}
}

func (r *Room) ServeHTTP(c *gin.Context) {
	upgrader.CheckOrigin = func(r *http.Request) bool { return true }

	socket, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Println("WebSocket upgrade failed:", err)
		return
	}

	client := &client{
		Socket: socket,
		Send:   make(chan *message, types.MessageBufferSize),
		Room:   r,
		Name:   "TEMP", // Change to dynamic value if needed
	}

	r.Join <- client

	go client.Write()
	client.Read()
}
