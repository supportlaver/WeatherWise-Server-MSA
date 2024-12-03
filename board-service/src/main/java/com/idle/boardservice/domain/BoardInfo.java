package com.idle.boardservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardInfo {
    private String title;
    private String content;

    public static BoardInfo of(String title , String content) {
        return BoardInfo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
