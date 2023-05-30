package jp.konosuba.notificationserver.user.token;

import jp.konosuba.notificationserver.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthToken,Long> {
    AuthToken getAuthTokenByToken(String token);
    AuthToken getAuthTokenByUser(User user);

}
