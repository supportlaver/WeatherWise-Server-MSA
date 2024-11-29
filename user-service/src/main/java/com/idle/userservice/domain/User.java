package com.idle.userservice.domain;
import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.base.BaseEntity;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
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
        if (exp <= 0) {
            throw new BaseException(ErrorCode.INVALID_EXP_VALUE);
        }
        int totalExp = this.exp.getValue() + exp;
        if (isLevelUp(totalExp)) levelUp(totalExp);
        else updateExp(totalExp);
    }

    private boolean isLevelUp(int totalExp) {
        return totalExp > 100;
    }
    private void levelUp(int totalExp) {
        this.level = this.level.levelUp();
        this.exp = this.exp.calculateExp(totalExp);
    }

    private void updateExp(int totalExp) {
        this.exp = Exp.from(totalExp);
    }
}
