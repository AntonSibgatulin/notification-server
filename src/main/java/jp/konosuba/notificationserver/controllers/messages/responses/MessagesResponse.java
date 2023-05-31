package jp.konosuba.notificationserver.controllers.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessagesResponse {
    private String message;
    private Integer code;

}
