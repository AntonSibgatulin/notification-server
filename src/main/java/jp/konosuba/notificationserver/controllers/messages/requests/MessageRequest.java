package jp.konosuba.notificationserver.controllers.messages.requests;

import lombok.Data;

import java.util.List;

@Data

public class MessageRequest {

    private String message;
    private List<Long> contacts;
    private Integer type;

}
