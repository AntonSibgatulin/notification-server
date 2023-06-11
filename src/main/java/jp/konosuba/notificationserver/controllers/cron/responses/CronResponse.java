package jp.konosuba.notificationserver.controllers.cron.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CronResponse {

    private String message;
    private Integer code;


}
