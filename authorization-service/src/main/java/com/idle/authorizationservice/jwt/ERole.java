package com.idle.authorizationservice.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Getter
@Slf4j
@AllArgsConstructor
public enum ERole {
    GUEST("ROLE_GUEST", "GUEST"), USER("ROLE_USER", "USER"), ADMIN("ROLE_ADMIN", "ADMIN");

    private final String roleCode;
    private final String displayName;

}
