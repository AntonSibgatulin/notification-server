package jp.konosuba.notificationserver.contact;

import jakarta.persistence.*;
import jp.konosuba.notificationserver.user.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    private Boolean relative;
    private Boolean tg;
    private Boolean vk;
    private Boolean ws; //whatsapp


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
