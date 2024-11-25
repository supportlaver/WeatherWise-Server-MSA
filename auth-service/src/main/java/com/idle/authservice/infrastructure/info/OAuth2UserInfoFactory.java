package com.idle.authservice.infrastructure.info;

import com.idle.commonservice.auth.EProvider;
import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(EProvider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
