package com.idle.boardservice.infrastructure;

import com.idle.boardservice.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board , Long> {
}
