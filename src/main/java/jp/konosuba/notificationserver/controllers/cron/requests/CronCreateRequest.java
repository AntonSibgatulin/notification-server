package jp.konosuba.notificationserver.controllers.cron.requests;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.cron.CronType;
import lombok.Data;

import java.util.List;

@Data
public class CronCreateRequest {

    private Integer codeFine;
    private String message;
    private String http;

    private List<Long> contacts;

    private CronType cronType;
}
