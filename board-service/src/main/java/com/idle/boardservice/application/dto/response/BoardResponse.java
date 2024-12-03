package com.idle.boardservice.application.dto.response;

import com.idle.boardservice.domain.Board;

import java.time.LocalDateTime;

public record BoardResponse(
        Long boardId,
        Long userId,
        String title,
        String content,
        String locationName,
        LocalDateTime createdAt
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getWriter().getUserId(),
                board.getBoardInfo().getTitle(),
                board.getBoardInfo().getContent(),
                board.getLocation().getLocationName(),
                board.getCreatedAt()
        );
    }
}
