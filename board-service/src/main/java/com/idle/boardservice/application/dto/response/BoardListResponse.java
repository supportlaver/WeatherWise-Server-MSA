package com.idle.boardservice.application.dto.response;

import com.idle.boardservice.domain.Board;
import org.springframework.data.domain.Page;

import java.util.List;

public record BoardListResponse(
        List<BoardResponse> boards
) {
    public static BoardListResponse from(List<Board> boards) {
        return new BoardListResponse(boards.stream()
                .map(BoardResponse::from)
                .toList());
    }

    public static BoardListResponse fromForPage(Page<Board> boards) {
        return new BoardListResponse(boards.stream()
                .map(BoardResponse::from)
                .toList());
    }
}
