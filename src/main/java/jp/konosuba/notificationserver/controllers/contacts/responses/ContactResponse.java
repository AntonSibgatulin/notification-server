package jp.konosuba.notificationserver.controllers.contacts.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContactResponse {
    private String message;
    private int code;

}
