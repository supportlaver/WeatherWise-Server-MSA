package com.idle.userservice.domain;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    @Query(value = "SELECT u.id as id, u.password as password, u.role as role" +
            " FROM User u WHERE u.serialId = :serialId")
    Optional<UserSecurityForm> findUserIdAndRoleBySerialId(@Param("serialId") String serialId);

    Optional<UserSecurityForm> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id, boolean b);
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.refreshToken = :refreshToken, u.isLogin = :isLogin where u.id = :userId")
    void updateRefreshTokenAndLoginStatus(@Param("userId") Long userId, @Param("refreshToken") String refreshToken, @Param("isLogin") Boolean isLogin);

    Optional<UserSecurityForm> findBySerialIdAndProvider(String serialId, EProvider provider);

    interface UserSecurityForm {
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
}
