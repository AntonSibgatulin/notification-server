package jp.konosuba.notificationserver.data.contact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jp.konosuba.notificationserver.data.user.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
