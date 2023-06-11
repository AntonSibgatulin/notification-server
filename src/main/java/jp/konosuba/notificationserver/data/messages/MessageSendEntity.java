package jp.konosuba.notificationserver.data.messages;


import jakarta.persistence.*;
import jp.konosuba.notificationserver.data.user.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MessageSendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long time;
    @Enumerated
    private StatusMessage statusMessage;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;



    private enum StatusMessage{
        SENDING,
        END
    }
}
