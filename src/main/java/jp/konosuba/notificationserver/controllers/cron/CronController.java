package jp.konosuba.notificationserver.controllers.cron;

import jakarta.validation.Valid;
import jp.konosuba.notificationserver.controllers.cron.requests.CronCreateRequest;
import jp.konosuba.notificationserver.controllers.cron.responses.CronResponse;
import jp.konosuba.notificationserver.controllers.cron.service.CronService;
import jp.konosuba.notificationserver.data.cron.Cron;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cron")
@RestController
public class CronController {

    private final CronService cronService;


    @PutMapping("/create")
    public ResponseEntity<CronResponse> createCron(@Valid @RequestBody CronCreateRequest cronCreateRequest){
        return this.cronService.createCron(cronCreateRequest);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Cron> getCronById(@PathVariable("id") Long id){
        return this.cronService.getCronById(id);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<CronResponse> deleteCron(@PathVariable("id") Long id){
        return this.cronService.deleteCron(id);
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<Cron> editCron(@PathVariable("id") Long id,@Valid @RequestBody CronCreateRequest cronCreateRequest){
        return this.cronService.editCron(cronCreateRequest,id);
    }

    @GetMapping("/getMyCrons")
    public List<Cron> getMyCron(){
        return this.cronService.getMyCrons();
    }
}
