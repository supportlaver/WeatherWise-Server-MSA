package com.idle.authservice.infrastructure.authentication.handler.signout;

import com.idle.authservice.infrastructure.authentication.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSignOutProcessHandler implements LogoutHandler {
    // private final UserRepository userRepository;

    @Override
    // @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        if (authentication == null) {
            return;
        }

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

//        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
//                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_HEADER));

        //kakaoLogoutService.logoutFromKakao(token);

        // userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), null, false);
    }
}
