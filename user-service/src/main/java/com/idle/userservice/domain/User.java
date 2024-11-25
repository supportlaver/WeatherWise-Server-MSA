package com.idle.userservice.domain;
import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.base.BaseEntity;
import com.idle.commonservice.jpa.PointConverter;
import com.idle.commonservice.model.Point;
import com.idle.userservice.presentation.dto.auth.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@ToString
@Entity
@Getter
@DynamicUpdate
@Table(name = "users") @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Convert(converter = PointConverter.class)
    private Point point;

    @Embedded
    private Level level;

    @Embedded
    private Password password;

    @Embedded
    private LoginId loginId;

    @Embedded
    private PersonalWeatherTraits personalWeatherTraits;

    @Column(name = "nickname", length = 12)
    private String nickname;
    /* User Status */
    @Column(name = "social_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "is_login", nullable = false)
    private Boolean isLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "is_completed_survey")
    private Boolean isCompletedSurvey;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    /**
     * 회원가입용
     */
    public static User from(UserRequest userRequest) {
        return User.builder()
                .serialId(userRequest.getSerialId())
                .password(Password.builder().value(userRequest.getPassword()).build())
                .provider(userRequest.getProvider())
                .role(userRequest.getRole())
                .point(Point.builder().value(0).build())
                .level(Level.builder().value(1).build())
                .nickname(userRequest.getNickname())
                .isLogin(false)
                .build();
    }
}
