package com.idle.boardservice.application;

import com.idle.boardservice.application.dto.response.BoardListResponse;
import com.idle.boardservice.application.dto.response.BoardResponse;
import com.idle.boardservice.domain.Board;
import com.idle.boardservice.domain.BoardRepository;
import com.idle.boardservice.infrastructure.UserServiceClient;
import com.idle.boardservice.presentation.dto.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final VoteCacheService voteCacheService;

    @Transactional
    public BoardResponse createBoard(Long userId, BoardRequest boardRequest) {
        Board board = Board.createNewBoard(userId, boardRequest.title(),
                boardRequest.content(), boardRequest.locationRequest().locationName(), boardRequest.locationRequest().latitude(),
                boardRequest.locationRequest().longitude());

        Board savedBoard = boardRepository.save(board);

        voteCacheService.initializeVote(savedBoard.getId());

        return BoardResponse.from(board);
    }

    public BoardResponse getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId);
        return BoardResponse.from(board);
    }

    public BoardListResponse getBoardsWithRadius(double latitude, double longitude) {
        List<Board> boards = boardRepository.findByLocationWithinRadius(latitude, longitude);
        return BoardListResponse.from(boards);
    }

}
