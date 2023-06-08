package jp.konosuba.notificationserver.data.messages;

import jakarta.persistence.*;
import jp.konosuba.notificationserver.data.contact.Contacts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class MessageObject {

    private Long id;


    private String message;

    private Long timeCreate;

    private Contacts[] contacts;


    private Integer typeMessage;


    public MessageObject(Long id, String message, Long timeCreate, List<Contacts> contacts, Integer typeMessage) {
        this.id = id;
        this.message = message;
        this.timeCreate = timeCreate;
        this.contacts = new Contacts[contacts.size()];
        for (int i = 0;i<contacts.size();i++){
            this.contacts[i] = contacts.get(i);
        }
        this.typeMessage = typeMessage;
    }
}
