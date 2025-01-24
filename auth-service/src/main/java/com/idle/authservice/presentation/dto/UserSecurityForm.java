package com.idle.authservice.presentation.dto;

import com.idle.commonservice.auth.ERole;

public interface UserSecurityForm {
    Long getId();
    String getPassword();
    ERole getRole();
}
