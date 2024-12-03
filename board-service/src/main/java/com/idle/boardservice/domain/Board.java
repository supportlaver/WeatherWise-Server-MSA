package com.idle.boardservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Embedded
    private Writer writer;

    @Embedded
    private BoardVote boardVote;

    @Embedded
    private Location location;

    @Embedded
    private BoardInfo boardInfo;

    public static Board createNewBoard(Long userId , String title , String content ,
                           String locationName , Double latitude , Double longitude ) {
        return Board.builder()
                .writer(Writer.of(userId))
                .boardInfo(BoardInfo.of(title,content))
                .location(Location.of(locationName,latitude,longitude))
                .boardVote(BoardVote.update(0,0))
                .build();
    }
}
