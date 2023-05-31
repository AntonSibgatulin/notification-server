package jp.konosuba.notificationserver.controllers.messages.responses;

import jp.konosuba.notificationserver.data.messages.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessagesListResponse {

    private List<MessageEntity> messageEntityList;
}
