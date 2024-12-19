package com.idle.authorizationservice.filter;

import com.idle.authorizationservice.jwt.Constants;
import com.idle.authorizationservice.jwt.ERole;
import com.idle.authorizationservice.jwt.JwtUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;
    private Key key;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public static class Config {
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            log.info("request URL = {} " , request.getURI());
            String path = request.getPath().toString();
            // 특정 경로를 필터에서 건너뛰기
            if (path.equals("/api/users/signup") || path.equals("/api/signup")) {
                log.info("건너뛰기");
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // Authorization 헤더에서 JWT 추출
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "").trim();

            // JWT에서 사용자 정보 추출
            JwtUserInfo userInfo = getUserInfoFromJwt(jwt);
            if (userInfo == null) {
                return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
            }

            // USER_ID 헤더 추가
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("USER_ID", String.valueOf(userInfo.id())) // 사용자 ID 추가
                    .build();

            // 교체된 요청으로 필터 체인 실행
            exchange.getRequest().mutate().header("X-Gateway-Header", String.valueOf(userInfo.id()));
/*
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            log.info("Adding USER_ID to headers: {}", userInfo.id());
            log.info("Modified Request Headers: {}", modifiedRequest.getHeaders());
*/

            return chain.filter(exchange);
        };
    }
    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        System.out.println("jwt = " + jwt);
        // jwt.io 에서 token 으로 subject 를 알 수 있다.
        String subject = null;
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        System.out.println(3);
        // Subject 를 추출했고, 올바른 값인지 확인하면 된다.
        // subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        JwtUserInfo userInfo = new JwtUserInfo(
                Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME, String.class)),
                ERole.valueOf(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class))
        );
        return returnValue;
    }

    // Mono(단일값) , Flux(다중값) -> Spring WebFlux 에서 나오는 개념
    // API 처리할 때 비동기 방식으로 처리
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        // Mono 타입으로 반환 -> setComplete()
        return response.setComplete();
    }

    private JwtUserInfo getUserInfoFromJwt(String jwt) {
        try {
            // JWT 파싱 및 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            // 사용자 정보 생성 및 반환
            return new JwtUserInfo(
                    Long.valueOf(claims.get(Constants.USER_ID_CLAIM_NAME, String.class)),
                    ERole.valueOf(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null; // JWT 검증 실패 시 null 반환
        }
    }
}
