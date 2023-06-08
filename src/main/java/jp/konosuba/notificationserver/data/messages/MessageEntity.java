package jp.konosuba.notificationserver.data.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.user.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "user" })
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(4096)")
    private String message;

    private Long timeCreate;

    @ManyToMany
    @JoinTable(name = "contacts_id")
    private List<Contacts> contacts;


    private Integer typeMessage;
    

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
