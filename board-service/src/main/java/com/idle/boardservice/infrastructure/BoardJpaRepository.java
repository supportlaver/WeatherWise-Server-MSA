package com.idle.boardservice.infrastructure;

import com.idle.boardservice.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardJpaRepository extends JpaRepository<Board , Long> {

    // 특정 위치 반경 5km 이내 게시글 목록을 조회 (Native Query)
    @Query(value = """
    SELECT b.*
    FROM board b
    WHERE ST_Distance_Sphere(
        point(b.longitude, b.latitude),  -- 경도, 위도 순서 (longitude, latitude)
        point(:longitude, :latitude)     -- 경도, 위도 순서
    ) <= 5000
    """, nativeQuery = true)
    List<Board> findByLocationWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
