package com.idle.boardservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.*;

@Embeddable
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardVote {
    @Enumerated(STRING)
    private VoteType voteType;
    private Integer upvoteCount;
    private Integer downvoteCount;

    public static BoardVote update(Integer upvoteCount , Integer downvoteCount) {
        return BoardVote.builder()
                .upvoteCount(upvoteCount)
                .downvoteCount(downvoteCount)
                .build();
    }
}
