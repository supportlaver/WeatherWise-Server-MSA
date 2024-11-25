package com.idle.authservice.infrastructure.info;


import com.idle.commonservice.auth.ERole;

public record JwtUserInfo(Long id, ERole role) {

}
