package com.idle.authservice.infrastructure.authentication.handler.signin;

import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.authentication.CustomUserDetails;
import com.idle.authservice.infrastructure.authentication.dto.JwtTokenDto;
import com.idle.authservice.infrastructure.authentication.util.CookieUtil;
import com.idle.authservice.infrastructure.authentication.util.JwtUtil;
import com.idle.authservice.presentation.dto.request.UpdateUserRefreshToken;
import com.idle.authservice.presentation.dto.response.UserResponse;
import com.idle.commonservice.auth.ERole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultSignInSuccessHandler implements AuthenticationSuccessHandler {
    // private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final UserServiceClient userServiceClient;

    @Value("${after-login.default-success}")
    private String LOGIN_URL;
    
    @Override
    // @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        ERole role = userPrincipal.getRole();

        // JWT Token 발급
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());


        // HTTP 요청 (동기)
        userServiceClient.updateRefreshTokenAndLoginStatus(UpdateUserRefreshToken
                .from(userPrincipal.getId(), jwtTokenDto.getRefreshToken()));
        // userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), jwtTokenDto.getRefreshToken(), true);

        // HTTP 요청 (동기)
        UserResponse res = userServiceClient.findById(userPrincipal.getId());
        /*String nickname = userRepository.findByIdForLegacy(userPrincipal.getId())
                .map(user -> StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getSerialId())
                .orElseThrow();*/

        // endpoint

        String userAgent = request.getHeader("User-Agent");

        boolean isMobile = userAgent.matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        if (userAgent.indexOf("IOS_APP") > -1 || userAgent.indexOf("ANDROID_APP") > -1) {
            log.info("앱");
            setSuccessAppResponse(response, jwtTokenDto);
        } else if (isMobile) {
            log.info("앱브라우저");
            setSuccessWebResponse(response, jwtTokenDto, res.getNickname(), role);
        } else {
            log.info("웹");
            setSuccessWebResponse(response, jwtTokenDto, res.getNickname(), role);
        }


    }

    private void setSuccessAppResponse(HttpServletResponse response, JwtTokenDto tokenDto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", Map.of(
                "accessToken", tokenDto.getAccessToken(),
                "refreshToken", tokenDto.getRefreshToken()
        ));
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(response));
    }

    private void setSuccessWebResponse(HttpServletResponse response, JwtTokenDto tokenDto, String nickname, ERole eRole) throws IOException {
        CookieUtil.addSecureCookie(response, "refreshToken", tokenDto.getRefreshToken(), jwtUtil.getWebRefreshTokenExpirationSecond());
        CookieUtil.addCookie(response, "accessToken", tokenDto.getAccessToken());
        //CookieUtil.addCookie(response, "nickname", URLEncoder.encode(nickname, StandardCharsets.UTF_8));
        //CookieUtil.addCookie(response, "role", eRole.getDisplayName());

        response.getWriter().println("OK");
    }
}
