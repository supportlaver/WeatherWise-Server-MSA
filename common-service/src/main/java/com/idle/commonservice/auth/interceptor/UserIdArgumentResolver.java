package com.idle.commonservice.auth.interceptor;


import com.idle.commonservice.annotation.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) //파라미터가 Long 타입이고
                && parameter.hasParameterAnnotation(UserId.class); //UserId 어노테이션일 때만 resolver 적용
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        System.out.println("UserIdArgumentResolver.resolveArgument");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String userIdHeader = request.getHeader("x-gateway-header");
        System.out.println("x-gateway-header = " + userIdHeader); // 헤더 값 출력
        return Long.valueOf(userIdHeader);
    }
}