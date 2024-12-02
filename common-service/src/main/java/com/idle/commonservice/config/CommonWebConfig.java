package com.idle.commonservice.config;

import com.idle.commonservice.auth.interceptor.UserIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CommonWebConfig implements WebMvcConfigurer {
    @Bean
    public UserIdArgumentResolver userIdArgumentResolver() {
        System.out.println("CommonWebConfig.userIdArgumentResolver");
        return new UserIdArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        System.out.println("CommonWebConfig.addArgumentResolvers");
        resolvers.add(userIdArgumentResolver());
    }
}
