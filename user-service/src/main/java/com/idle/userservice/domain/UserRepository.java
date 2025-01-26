package com.idle.userservice.domain;

import com.idle.commonservice.auth.EProvider;
import com.idle.userservice.application.dto.request.UserSecurityForm;

import java.util.Optional;

public interface UserRepository {
    Optional<UserSecurityForm> findUserIdAndRoleBySerialId(String serialId);

    Optional<User> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id, boolean b);

    void updateRefreshTokenAndLoginStatus(Long userId,String refreshToken, Boolean isLogin);

    Optional<User> findBySerialIdAndProvider(String serialId, EProvider provider);

    Optional<User> findByNicknameAndIsDeleted(String nickname, Boolean isDeleted);
    Optional<User> findById(Long id);
    User save(User user);
    boolean hasCoupon(Long userId, Long couponId);
}
