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

            if (path.equals("/api/users/signup") || path.equals("/api/signup") || path.equals("/api/v1/sign-in")) {
                log.info("건너뛰기");
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }


            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "").trim();


            JwtUserInfo userInfo = getUserInfoFromJwt(jwt);
            if (userInfo == null) {
                return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
            }

            // USER_ID 헤더 추가
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("USER_ID", String.valueOf(userInfo.id()))
                    .build();

            exchange.getRequest().mutate().header("X-Gateway-Header", String.valueOf(userInfo.id()));

            return chain.filter(exchange);
        };
    }


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
            return null;
        }
    }
}
