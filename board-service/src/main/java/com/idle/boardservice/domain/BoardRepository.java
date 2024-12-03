package com.idle.boardservice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);
    Board findById(Long boardId);
    List<Board> findByLocationWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
