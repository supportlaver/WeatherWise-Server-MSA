package com.idle.userservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.domain.User;
import com.idle.userservice.domain.UserCoupon;
import com.idle.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCouponService {
    private final UserRepository userRepository;

    @Transactional
    public void issuedCoupon(Long userId, Long couponId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        user.issuedCoupon(UserCoupon.issuedCoupon(couponId , userId));
    }

    public boolean hasCoupon(Long userId, Long couponId) {
        return userRepository.hasCoupon(userId , couponId);
    }
}
