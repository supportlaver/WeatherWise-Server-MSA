package com.idle.userservice.presentation;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.base.BaseResponse;
import com.idle.userservice.application.UserAuthService;
import com.idle.userservice.application.dto.request.AuthSignUpRequest;
import com.idle.userservice.application.dto.request.UpdateUserRefreshToken;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.application.dto.response.AuthSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;


    @PostMapping("/refresh-token")
    public void updateRefreshTokenAndLoginStatus(@RequestBody UpdateUserRefreshToken req) {
        userAuthService.updateRefreshTokenAndLoginStatus(req);
    }

    @GetMapping("/serial-id-provider")
    public void findBySerialIdAndProvider(@RequestParam("serialId") String serialId,
                                          @RequestParam("provider") EProvider provider) {
        System.out.println("JIWON?");
        userAuthService.findBySerialIdAndProvider(serialId, provider);
    }

    @GetMapping("/user-id-role")
    public ResponseEntity<UserSecurityFormDto> findUserIdAndRoleBySerialId(@RequestParam("serial-id") String serialId) {
        return ResponseEntity.ok().body(userAuthService.findUserIdAndRoleBySerialId(serialId));
    }

    @GetMapping("/login-refresh-token")
    public ResponseEntity<UserSecurityFormDto>
    findByIdAndIsLoginAndRefreshTokenIsNotNull(@RequestParam("user-id") Long id ,
                                               @RequestParam("refresh-token-null") boolean b) {
        System.out.println("JIWON!");
        return ResponseEntity.ok().body(userAuthService.findByIdAndIsLoginAndRefreshTokenIsNotNull(id,b));
    }
}
