package com.idle.authservice.infrastructure.auth.handler.signin;

import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.auth.CustomUserDetails;
import com.idle.authservice.infrastructure.auth.dto.JwtTokenDto;
import com.idle.authservice.infrastructure.auth.util.CookieUtil;
import com.idle.authservice.infrastructure.auth.util.JwtUtil;
import com.idle.authservice.presentation.dto.request.UpdateUserRefreshToken;
import com.idle.authservice.presentation.dto.response.UserResponse;
import com.idle.commonservice.auth.ERole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;
    // private final UserRepository userRepository;

    @Value("${after-login.oauth2-success}")
    private String LOGIN_URL;

    @Value("${after-login.oauth2-success-guest}")
    private String LOGIN_URL_GUEST;

    @Override
    // @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());
        userServiceClient.updateRefreshTokenAndLoginStatus(UpdateUserRefreshToken
                .from(userPrincipal.getId(), jwtTokenDto.getRefreshToken()));
        // userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), jwtTokenDto.getRefreshToken(), true);

        UserResponse res = userServiceClient.findById(userPrincipal.getId());
        // String nickname = userRepository.findByIdForLegacy(userPrincipal.getId()).map(UserEntity::getNickname).orElse("");

        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokenDto.getRefreshToken(), jwtUtil.getWebRefreshTokenExpirationSecond());
        CookieUtil.addCookie(response, "accessToken", jwtTokenDto.getAccessToken());
        CookieUtil.addCookie(response, "nickname", URLEncoder.encode(res.getNickname(), StandardCharsets.UTF_8));
        CookieUtil.addCookie(response, "role", userPrincipal.getRole().getDisplayName());

        if (userPrincipal.getRole() == ERole.GUEST) {
            response.sendRedirect(LOGIN_URL_GUEST);
        } else {
            response.sendRedirect(LOGIN_URL);
        }
    }
}
