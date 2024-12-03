package com.idle.boardservice.infrastructure;

import com.idle.boardservice.application.VoteCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisVoteCacheService implements VoteCacheService {
    private final RedisTemplate<String, Integer> redisTemplate;
    private static final String UPVOTE_KEY_PREFIX = "board:upvote:";
    private static final String DOWNVOTE_KEY_PREFIX = "board:downvote:";

    @Override
    public void initializeVote(Long boardId) {
        String upvoteKey = UPVOTE_KEY_PREFIX + boardId;
        String downvoteKey = DOWNVOTE_KEY_PREFIX + boardId;
        redisTemplate.opsForValue().set(upvoteKey, 0);
        redisTemplate.opsForValue().set(downvoteKey, 0);
    }
}
