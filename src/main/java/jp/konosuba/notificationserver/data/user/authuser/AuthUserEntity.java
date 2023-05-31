package jp.konosuba.notificationserver.data.user.authuser;

import jakarta.persistence.*;
import jp.konosuba.notificationserver.data.user.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "auth_users")
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer code;
}
