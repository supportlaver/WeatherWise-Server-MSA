package com.idle.boardservice.infrastructure;

import com.idle.boardservice.domain.Board;
import com.idle.boardservice.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private final BoardJpaRepository boardRepository;
    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }
}
