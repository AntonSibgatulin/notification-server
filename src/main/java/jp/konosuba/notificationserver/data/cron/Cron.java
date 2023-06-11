package jp.konosuba.notificationserver.data.cron;

import jakarta.persistence.*;
import jp.konosuba.notificationserver.data.contact.Contacts;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Integer codeFine;
    private String message;
    private String http;
    @ManyToMany
    @JoinTable
    private List<Contacts> contacts;

    @Enumerated(EnumType.STRING)
    private CronType cronType;


    @Enumerated(EnumType.STRING)
    private CronStatus cronStatus;

}
