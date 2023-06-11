package jp.konosuba.notificationserver.controllers.cron.service;

import jp.konosuba.notificationserver.NotificationServerApplication;
import jp.konosuba.notificationserver.controllers.cron.requests.CronCreateRequest;
import jp.konosuba.notificationserver.controllers.cron.responses.CronResponse;
import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.contact.ContactsRepository;
import jp.konosuba.notificationserver.data.cron.Cron;
import jp.konosuba.notificationserver.data.cron.CronRepository;
import jp.konosuba.notificationserver.data.cron.CronStatus;
import jp.konosuba.notificationserver.data.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.ClassUtils;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public record CronService(CronRepository cronRepository,
                          UserRepository userRepository,
                          ContactsRepository contactsRepository,
                          KafkaTemplate<String,String> kafkaTemplate) {




    public ResponseEntity<CronResponse> createCron(CronCreateRequest cronCreateRequest){

        var cron = StringUtils.fromCronCreateRequestToCron(cronCreateRequest);
        List<Contacts> list = contactsRepository.findByUserIds(cronCreateRequest.getContacts());
        cron.setContacts(list);
        cron.setCronStatus(CronStatus.OK);

        var user = StringUtils.getUser();
        cron.setUserId(user.getId());

        cronRepository.save(cron);
        var jsonObject = new JSONObject(ClassUtils.fromObjectToJson(cron));
        jsonObject.put("typeOperation","new_cron");

        kafkaTemplate.send(NotificationServerApplication.name_of_topic,jsonObject.toString());

        return ResponseEntity.ok(new CronResponse("fine", Code.OK));
    }


    public ResponseEntity<Cron> getCronById(Long id){
        var cron = cronRepository.getReferenceById(id);

        if(cron!=null){
            return ResponseEntity.ok(cron);
        }else{
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
    }

}
