package com.idle.boardservice.presentation.dto.request;

public record BoardRequest(
        String title,
        String content,
        LocationRequest locationRequest
) {
}
