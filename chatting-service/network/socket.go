package network

import (
	"chatting-service/service"
	"chatting-service/types"
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
)

// Upgrader 는 HTTP Connection 을 WebSocket 으로 Upgrader 를 쓰는 것
var upgrader = &websocket.Upgrader{ReadBufferSize: types.SocketBufferSize,
	WriteBufferSize: types.MessageBufferSize, CheckOrigin: func(r *http.Request) bool { return true }}

// 채팅방에 대한 값을 가지는 구조체
type Room struct {
	// 수신되는 메시지를 보관하는 값
	// 들어오는 메시지를 다른 클라이언트들에게 전송
	Forward chan *message

	// 소켓이 연결됐을 때 작동
	Join chan *client

	// 소켓이 끊어질 때 작동
	Leave chan *client

	// 현재 방에 있는 Client 정보를 저장
	Clients map[*client]bool

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

// Room 초기화 함수
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
	// 클라이언트가 들어오는 메시지를 읽는 함수
	defer c.Socket.Close()
	for {
		var msg *message
		err := c.Socket.ReadJSON(&msg)

		if err != nil {
			// Close 가 된 Error 인 경우에 대해서는 break 를 걸어준다.
			if !websocket.IsUnexpectedCloseError(err, websocket.CloseGoingAway) {
				break
			} else {
				// 그렇지 않은 경우에는 panic 에러
				panic(err)
			}

		} else {
			log.Println("READ : ", msg, "client", c.Name)
			log.Println()
			msg.When = time.Now().Local()
			msg.Name = c.Name
			c.Room.Forward <- msg
		}
	}
}

func (c *client) Write() {
	defer c.Socket.Close()
	// 클라이언트가 메시지를 전송하는 함수
	for msg := range c.Send {
		log.Println("WRITE : ", msg, "client", c.Name)
		log.Println()

		err := c.Socket.WriteJSON(msg)
		if err != nil {
			panic(err)
		}
	}
}

func (r *Room) Run() {
	for {
		select {
		case client := <-r.Join:
			r.Clients[client] = true
		case client := <-r.Leave:
			r.Clients[client] = false
			close(client.Send)
			delete(r.Clients, client)
		case msg := <-r.Forward:
			go r.service.InsertChatting(msg.Name, msg.Message, msg.Room)
			for client := range r.Clients {
				client.Send <- msg
			}
		}
	}
}

func (r *Room) ServeHTTP(c *gin.Context) {
	// 이후 요청이 이렇게 들어오게 된다면 Upgrade를 통해서 소켓을 가져 온다.

	upgrader.CheckOrigin = func(r *http.Request) bool {
		return true
	}

	Socket, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Fatal("---- serveHTTP:", err)
		return
	}

	/*	authCookie, err := c.Request.Cookie("auth")
		if err != nil {
			log.Fatal("auth cookie is failed", err)
			return
		}*/

	// 문제가 없다면 client를 생성하여 방에 입장했다고 채널에 전송한다.
	client := &client{
		Socket: Socket,
		Send:   make(chan *message, types.MessageBufferSize),
		Room:   r,
		Name:   "TEMP",
	}

	r.Join <- client

	// 또한 defer를 통해서 client가 끝날 떄를 대비하여 퇴장하는 작업을 연기한다.
	defer func() { r.Leave <- client }()

	// 이 후 고루틴을 통해서 write를 실행 시킨다.
	go client.Write()

	client.Read()
}
