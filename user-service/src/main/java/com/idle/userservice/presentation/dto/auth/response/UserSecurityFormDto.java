package com.idle.userservice.presentation.dto.auth.response;

import com.idle.commonservice.auth.ERole;
import com.idle.userservice.domain.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurityFormDto implements UserSecurityForm {
    private Long id;
    private String password;
    private ERole role;


/*    public static UserSecurityForm toSecurityForm(UserSecurityFormDto dto) {
        return new UserSecurityForm() {
            @Override
            public Long getId() {
                return dto.getId();
            }

            @Override
            public String getPassword() {
                return dto.getPassword();
            }

            @Override
            public ERole getRole() {
                return dto.getRole();
            }
        };
    }*/
}
