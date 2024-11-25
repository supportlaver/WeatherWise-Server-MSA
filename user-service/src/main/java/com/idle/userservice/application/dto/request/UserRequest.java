package com.idle.userservice.application.dto.request;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String serialId;
    private String password;
    private EProvider provider;
    private ERole role;
    private String nickname;
}

