package com.idle.createdmissionservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

/**
 * <<Aggregate Root>>
 **/

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreatedMission extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "created_mission_id")
    private Long id;

    @Embedded
    private BasedMission basedMission;

    @Enumerated(STRING)
    private MissionTime missionTime;

    @Embedded
    private Challenger challenger;

    @Embedded
    private ImageFileInfo imageFileInfo;

    private boolean isCompleted;

    public void updateImageFileInfo(String originalFileName, String uploadFileUrl) {
        this.imageFileInfo = ImageFileInfo.of(originalFileName, uploadFileUrl);
    }

    public void completedMission() {
        this.isCompleted = true;
    }
}
