package jp.konosuba.notificationserver.controllers.cron;

import jp.konosuba.notificationserver.controllers.cron.service.CronService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cron")
@RestController
public class CronController {

    private final CronService cronService;

}
