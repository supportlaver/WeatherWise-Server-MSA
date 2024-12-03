package com.idle.boardservice.application;

import com.idle.boardservice.application.dto.response.BoardResponse;
import com.idle.boardservice.domain.Board;
import com.idle.boardservice.domain.BoardRepository;
import com.idle.boardservice.infrastructure.UserServiceClient;
import com.idle.boardservice.infrastructure.dto.response.UserResponse;
import com.idle.boardservice.presentation.dto.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserServiceClient userClient;
    private static final String UPVOTE_KEY = "board:upvote";
    private static final String DOWNVOTE_KEY = "board:downvote";
    private final RedisTemplate<String, Integer> redisTemplate;

    @Transactional
    public BoardResponse createBoard(Long userId, BoardRequest boardRequest) {
        Board board = Board.createNewBoard(userId, boardRequest.title(),
                boardRequest.content(), boardRequest.locationRequest().locationName(), boardRequest.locationRequest().latitude(),
                boardRequest.locationRequest().longitude());

        Board savedBoard = boardRepository.save(board);

        String upvoteKey = UPVOTE_KEY + savedBoard.getId();
        String downvoteKey = DOWNVOTE_KEY + savedBoard.getId();
        redisTemplate.opsForValue().set(upvoteKey, 0);
        redisTemplate.opsForValue().set(downvoteKey, 0);
        return BoardResponse.from(board);
    }

}
