package com.idle.boardservice.presentation.dto.request;

public record LocationRequest(
        String locationName,
        Double latitude,
        Double longitude
) {
}
