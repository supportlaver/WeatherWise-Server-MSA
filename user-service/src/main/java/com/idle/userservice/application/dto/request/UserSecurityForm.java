package com.idle.userservice.application.dto.request;

import com.idle.commonservice.auth.ERole;
import com.idle.userservice.domain.User;

public interface UserSecurityForm {
    static UserSecurityForm invoke(User user) {
        return new UserSecurityForm() {
            @Override
            public Long getId() {
                return user.getId();
            }

            @Override
            public String getPassword() {
                return user.getPassword().getValue();
            }

            @Override
            public ERole getRole() {
                return user.getRole();
            }
        };
    }

    Long getId();

    String getPassword();

    ERole getRole();
}
