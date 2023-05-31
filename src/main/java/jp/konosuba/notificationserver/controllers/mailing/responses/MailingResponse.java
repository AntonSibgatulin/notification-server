package jp.konosuba.notificationserver.controllers.mailing.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailingResponse {

    private String message;
    private Integer code;

}
