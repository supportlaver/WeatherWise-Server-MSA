package com.idle.userservice.presentation.dto.auth.response;

import com.idle.commonservice.auth.ERole;

public interface UserSecurityForm {
    Long getId(); // 사용자 고유 ID
    String getPassword(); // 암호화된 비밀번호
    ERole getRole(); // 사용자 역할
}
