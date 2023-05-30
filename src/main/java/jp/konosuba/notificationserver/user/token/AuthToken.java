package jp.konosuba.notificationserver.user.token;

import jakarta.persistence.*;
import jp.konosuba.notificationserver.user.user.User;
import lombok.Data;

@Data
@Entity
@Table(name = "tokens")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(600)",nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
