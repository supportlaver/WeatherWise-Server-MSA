package com.idle.boardservice.infrastructure;

import com.idle.boardservice.domain.Board;
import com.idle.boardservice.domain.BoardRepository;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private final BoardJpaRepository boardRepository;
    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_BOARD));
    }

    @Override
    public List<Board> findByLocationWithinRadius(double latitude, double longitude) {
        return boardRepository.findByLocationWithinRadius(latitude , longitude);
    }

}
