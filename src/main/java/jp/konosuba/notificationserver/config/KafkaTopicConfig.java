package jp.konosuba.notificationserver.config;

import jp.konosuba.notificationserver.NotificationServerApplication;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic notificatorTopic(){
        return TopicBuilder.name(NotificationServerApplication.name_of_topic).partitions(NotificationServerApplication.count_of_consumers).replicas(2).build();
    }
}
