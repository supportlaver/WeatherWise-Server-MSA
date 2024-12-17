package types

import "strings"

// 말그대로 버퍼 사이즈
// websocket 에서 사용할 버퍼 사이즈를 정하는 것
// 이미지나 동영상도 왔다 갔다 해야한다면 BufferSize 를 크게 만들면 된다.
// 무작정 크다고 좋은 것은 아니지만 트래픽이 많아지게 된다면 높아지는 것을 고려
const (
	SocketBufferSize  = 1024
	MessageBufferSize = 256
)

type header struct {
	Result int    `json:"result"`
	Data   string `json:"data"`
}

func newHeader(result int, data ...string) *header {
	return &header{
		Result: result,
		Data:   strings.Join(data, ","),
	}
}

type response struct {
	*header
	Result interface{} `json:"result"`
}

func NewRes(result int, res interface{}, data ...string) *response {
	return &response{
		header: newHeader(result, data...),
		Result: res,
	}
}
