package com.idle.userservice.infrastructure;

import com.idle.commonservice.auth.EProvider;
import com.idle.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User , Long> {
    @Query(value = "SELECT u.id as id, u.password as password, u.role as role" +
            " FROM User u WHERE u.serialId = :serialId")
    Optional<User> findUserIdAndRoleBySerialId(@Param("serialId") String serialId);

    Optional<User> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id, boolean b);
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.refreshToken = :refreshToken, u.isLogin = :isLogin where u.id = :userId")
    void updateRefreshTokenAndLoginStatus(@Param("userId") Long userId, @Param("refreshToken") String refreshToken, @Param("isLogin") Boolean isLogin);

    Optional<User> findBySerialIdAndProvider(String serialId, EProvider provider);

    Optional<User> findByNicknameAndIsDeleted(@Param("nickname")String nickname, @Param("isDeleted")Boolean isDeleted);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u JOIN u.coupons c " +
            "WHERE u.id = :userId AND c.couponId.couponId = :couponId")
    boolean hasCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u JOIN u.coupons c " +
            "WHERE u.id = :userId AND c.couponId.couponId = :couponId")
    boolean existsCouponByUserIdAndCouponId(@Param("userId") Long userId, @Param("couponId") Long couponId);
}
