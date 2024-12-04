package com.idle.userservice.infrastructure;

import com.idle.commonservice.auth.EProvider;
import com.idle.userservice.domain.User;
import com.idle.userservice.domain.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findUserIdAndRoleBySerialId(String serialId) {
        return userJpaRepository.findUserIdAndRoleBySerialId(serialId);
    }

    @Override
    public Optional<User> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id, boolean b) {
        return userJpaRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(id,b);
    }

    @Override
    public void updateRefreshTokenAndLoginStatus(Long userId, String refreshToken, Boolean isLogin) {
        userJpaRepository.updateRefreshTokenAndLoginStatus(userId,refreshToken,isLogin);
    }

    @Override
    public Optional<User> findBySerialIdAndProvider(String serialId, EProvider provider) {
        return userJpaRepository.findBySerialIdAndProvider(serialId,provider);
    }

    @Override
    public Optional<User> findByNicknameAndIsDeleted(String nickname, Boolean isDeleted) {
        return userJpaRepository.findByNicknameAndIsDeleted(nickname,isDeleted);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public boolean hasCoupon(Long userId, Long couponId) {
        return userJpaRepository.hasCoupon(userId , couponId);
    }
}
