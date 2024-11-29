package com.idle.userservice.domain;
import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.base.BaseEntity;
import com.idle.commonservice.jpa.LevelConverter;
import com.idle.commonservice.jpa.ExpConverter;
import com.idle.commonservice.model.Level;
import com.idle.commonservice.model.Exp;
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

    @Convert(converter = ExpConverter.class)
    private Exp exp;

    @Convert(converter = LevelConverter.class)
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
     * 회원가입용 (소셜)
     */
    public static User signUpSocialLogin(String serialId , String password , EProvider provider , ERole role , String nicName) {
        return User.builder()
                .serialId(serialId)
                .password(Password.from(password))
                .provider(provider)
                .role(role)
                .exp(Exp.from(0))
                .level(Level.from(1))
                .nickname(nicName)
                .isLogin(true)
                .isDeleted(false)
                .build();
    }

    public void acquisitionExp(int exp) {
        int totalExp = this.exp.getValue() + exp;

        if (totalExp > 100) {
            levelUp();
            calculateExp(totalExp);
        } else calculateExp(totalExp);
    }

    private void levelUp() {
        this.level = this.level.levelUp();
    }

    private void calculateExp(int totalExp) {
        this.exp = this.exp.calculateExp(totalExp);
    }

    // 도메인이 너무 커지면 Entity 자체가 너무 비대해진다 -> Service 계층이 entity 로 들어가는 느낌
    // 순수하게 도메인의 기능이 맞는가 ?
    // 회사마다 달라질 수 있는? (이상과 현실의 차이)
    // 퍼실레이터님 : 현실과 이상이 다르기 때문에 정통 DDD 를 꼭 고집할 필요는 없을 수 있다.
}
