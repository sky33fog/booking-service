package com.example.booking_service.listener;

import com.example.booking_service.model.kafka.CreateUserEvent;
import com.example.booking_service.model.mongo.UserStatistics;
import com.example.booking_service.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaCreateUserListener {

    private final UserStatisticsRepository userStatisticsRepository;

    @KafkaListener(topics = "${app.kafka.kafkaUserTopic}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaCreateUserEventConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload CreateUserEvent event,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        UUID uuid = UUID.randomUUID();

        userStatisticsRepository.save(new UserStatistics(uuid.toString(),
                event.getUserId(), LocalDateTime.now().toString()));

//        Optional<UserStatistics> userStat = userStatisticsRepository.findById(uuid.toString());
//
//        log.info("Received message: {}", userStat.orElseThrow(() -> new EntityNotFoundException("Entity in MongoDB not found.")).toString());
//        log.info("Partition: {}; Topic: {}, Timestamp: {}", partition, topic, timestamp);
    }
}
