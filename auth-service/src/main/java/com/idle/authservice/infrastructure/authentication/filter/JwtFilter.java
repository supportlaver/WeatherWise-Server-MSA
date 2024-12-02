package com.idle.authservice.infrastructure.authentication.filter;

import com.idle.authservice.infrastructure.authentication.constant.Constants;
import com.idle.authservice.infrastructure.authentication.provider.CustomAuthenticationProvider;
import com.idle.authservice.infrastructure.authentication.util.HeaderUtil;
import com.idle.authservice.infrastructure.authentication.util.JwtUtil;
import com.idle.authservice.infrastructure.info.JwtUserInfo;
import com.idle.commonservice.auth.ERole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("REQUEST : [{}] {}", request.getMethod(), request.getRequestURI());

        String path = request.getRequestURI();

        // /api/test/** 경로에 대해 필터 제외
        if (path.startsWith("/test")) {
            filterChain.doFilter(request, response);
            return;
        }

        /*if (path.startsWith("/api")) {
            filterChain.doFilter(request, response);
            return;
        }*/

        // Request Header에서 토큰 추출
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElse(null);

        if (token == null || "undefined".equals(token)) {
            log.info("토큰 X : [{}] {}", request.getMethod(), request.getRequestURI());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing or invalid authorization header");
            return;
        }

        //토근 검증
        Claims claims = jwtUtil.validateToken(token);

        JwtUserInfo userInfo = new JwtUserInfo(
                Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME, String.class)),
                ERole.valueOf(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class))
        );

        // 사용자 ID를 헤더에 추가 (헤더 수정)
        request = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("USER_ID".equals(name)) {
                    return String.valueOf(userInfo.id());
                }
                return super.getHeader(name);
            }
        };
        log.info("request = {} " , request.getHeader("USER_ID"));

        // 인증 전 객체
        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(
                userInfo,
                null,
                null
        );

        //인증 후 객체 생성
        UsernamePasswordAuthenticationToken afterAuthentication =
                (UsernamePasswordAuthenticationToken) customAuthenticationProvider.authenticate(beforeAuthentication);

        //인증 후 객체에 요정 정보 추가
        afterAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //객체 넣어주기
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(afterAuthentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(Constants.NO_NEED_AUTH_URLS).anyMatch(path::startsWith);
    }

}

