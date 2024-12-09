package repository

import (
	"chatting-service/config"
	"chatting-service/repository/kafka"
	"chatting-service/types/schema"
	"database/sql"
	"strings"

	// _ ?
	_ "github.com/go-sql-driver/mysql"
)

type Repository struct {
	cfg   *config.Config
	db    *sql.DB
	Kafka *kafka.Kafka
}

const (
	room       = "chatting.room"
	chat       = "chatting.chat"
	serverInfo = "chatting.serverInfo"
)

func NewRepository(cfg *config.Config) (*Repository, error) {
	r := &Repository{cfg: cfg}
	var err error

	if r.db, err = sql.Open(cfg.DB.Database, cfg.DB.URL); err != nil {
		return nil, err
	} else if r.Kafka, err = kafka.NewKafka(cfg); err != nil {
		return nil, err
	} else {
		return r, nil
	}
}

type ChatEvent struct {
	User     string
	Message  string
	RoomName string
}

func (s *Repository) GetChatList(roomName string) ([]*schema.Chat, error) {
	// when 에는 `` 를 하는 이유
	// when 은 많이 사용하는 단어이기 때문에 room 필드에 있는 when 이라는 것을 정확하게 알려주기 위함
	qs := query([]string{"SELECT * FROM", chat, "WHERE room = ? ORDER BY `when` DESC LIMIT 10"})

	if cursor, err := s.db.Query(qs, roomName); err != nil {
		return nil, err
	} else {
		// defer 로 할당된 메모리 관리
		defer cursor.Close()

		var result []*schema.Chat

		for cursor.Next() {
			d := new(schema.Chat)

			if err = cursor.Scan(&d.ID, &d.Room, &d.Name, &d.Message, &d.When); err != nil {
				return nil, err
			} else {
				result = append(result, d)
			}
		}

		if len(result) == 0 {
			return []*schema.Chat{}, nil
		} else {
			return result, nil
		}
	}
}

func (s *Repository) RoomList() ([]*schema.Room, error) {
	qs := query([]string{"SELECT * FROM", room})

	// cursor 는 기본적으로 메모리 할당
	if cursor, err := s.db.Query(qs); err != nil {
		return nil, err
	} else {
		// defer 로 할당된 메모리 관리
		defer cursor.Close()

		var result []*schema.Room

		for cursor.Next() {
			d := new(schema.Room)

			if err = cursor.Scan(&d.ID, &d.Name, &d.CreateAt, &d.UpdateAt); err != nil {
				return nil, err
			} else {
				result = append(result, d)
			}
		}

		if len(result) == 0 {
			return []*schema.Room{}, nil
		} else {
			return result, nil
		}
	}
}

func (s *Repository) MakeRoom(name string) error {
	_, err := s.db.Exec("INSERT INTO chatting.room(name) VALUES(?)", name)
	return err
}

func (s *Repository) Room(name string) (*schema.Room, error) {
	d := new(schema.Room)
	// SELECT * FROM chatting.room WHERE name = ?
	// 간단한 유틸 함수로 사용하기 위함
	qs := query([]string{"SELECT * FROM", room, "WHERE name = ?"})

	err := s.db.QueryRow(qs, name).Scan(
		&d.ID,
		&d.Name,
		&d.CreateAt,
		&d.UpdateAt,
	)

	// 여기서 sql: no row in result set 이라는 오류가 발생하기 때문에 방어 로직 설계
	// 구문으로 넘어오기 때문에 noResult 라는 유틸 함수를 생성
	if err = noResult(err); err != nil {
		return nil, err
	} else {
		return nil, nil
	}

	return d, err
}

func query(qs []string) string {
	return strings.Join(qs, " ") + ";"
}

func noResult(err error) error {
	if strings.Contains(err.Error(), "sql: no rows in result set") {
		return nil
	} else {
		return err
	}
}
