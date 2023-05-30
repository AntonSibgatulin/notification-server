package jp.konosuba.notificationserver.user.authuser;

import jp.konosuba.notificationserver.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUserEntity,Long> {
    AuthUserEntity getAuthUserEntitiesByCodeAndUser(Integer code, User user);
}
