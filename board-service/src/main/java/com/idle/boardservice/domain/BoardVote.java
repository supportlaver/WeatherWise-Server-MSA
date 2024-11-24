package com.idle.boardservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import static jakarta.persistence.EnumType.*;

@Embeddable
public class BoardVote {
    @Enumerated(STRING)
    private VoteType voteType;
}
