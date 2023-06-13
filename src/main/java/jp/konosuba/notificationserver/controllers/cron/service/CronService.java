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

        var user = StringUtils.getUser();
        List<Cron> getCron = cronRepository.getCronByUserId(user.getId());
        if(getCron.size()>=3){
            return ResponseEntity.status(Code.ACCESS_DENIED).body(new CronResponse("TO_MANY_CRONS",Code.ACCESS_DENIED));
        }

        var cron = StringUtils.fromCronCreateRequestToCron(cronCreateRequest);

        List<Contacts> list = contactsRepository.findAllById(cronCreateRequest.getContacts());
        System.out.println(ClassUtils.fromObjectToJson(list));
        cron.setContacts(list);
        cron.setCronStatus(CronStatus.OK);

        cron.setUserId(user.getId());

        cronRepository.save(cron);
        
        //////////////////////
        var jsonObject = new JSONObject();
        jsonObject.put("typeOperation","new_cron");
        jsonObject.put("cron",new JSONObject(ClassUtils.fromObjectToJson(cron)));
        sendMessageInKafka(jsonObject.toString());
        //////////////////////

        return ResponseEntity.ok(new CronResponse("fine", Code.OK));
    }


    public ResponseEntity<Cron> getCronById(Long id){
        var cron = cronRepository.getReferenceById(id);

        if(cron!=null && cron.getUserId() == StringUtils.getUser().getId()){
            return ResponseEntity.ok(cron);
        }else{
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
    }



    public ResponseEntity<CronResponse> deleteCron(Long id){
        var cron = cronRepository.getReferenceById(id);
        if(cron == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(new CronResponse("NOT_FOUND",Code.NOT_FOUND));
        }
        var user = StringUtils.getUser();
        if (user.getId() == cron.getUserId()) {
            cronRepository.deleteById(id);

            //////////////////////////////////
            var jsonObject = new JSONObject();
            jsonObject.put("cronId",cron.getId());
            jsonObject.put("typeOperation","delete_cron");
            sendMessageInKafka(jsonObject.toString());
            //////////////////////////////////

            return ResponseEntity.ok(new CronResponse("ok", Code.OK));
        }else{
            return ResponseEntity.status(Code.NOT_FOUND).body(new CronResponse("NOT_FOUND",Code.NOT_FOUND));

        }
    }


    public ResponseEntity<Cron> editCron(CronCreateRequest cronCreateRequest,Long id){
        var user = StringUtils.getUser();

        var cron = cronRepository.getReferenceById(id);
        if(cron == null || cron.getUserId() != user.getId()){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }

        cron.setCodeFine(cronCreateRequest.getCodeFine());
        cron.setMessage(cronCreateRequest.getMessage());
        cron.setHttp(cronCreateRequest.getHttp());

        List<Contacts> contacts = contactsRepository.findAllById(cronCreateRequest.getContacts());
        cron.setContacts(contacts);
        cron.setCronType(cronCreateRequest.getCronType());

        cronRepository.save(cron);


        //////////////////////////////
        var jsonObject = new JSONObject();
        jsonObject.put("typeOperation","edit_cron");
        jsonObject.put("cron",new JSONObject(ClassUtils.fromObjectToJson(cron)));
        sendMessageInKafka(jsonObject.toString());
        /////////////////////////////


        return ResponseEntity.ok(cron);

    }


    public List<Cron> getMyCrons(){
        var user = StringUtils.getUser();
        return cronRepository.getCronByUserId(user.getId());
    }



    public void sendMessageInKafka(String message){
        kafkaTemplate.send(NotificationServerApplication.topicMainControllerReader,message);

    }

}
