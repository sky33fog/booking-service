package com.example.booking_service.listener;

import com.example.booking_service.model.kafka.BookingRoomEvent;
import com.example.booking_service.model.mongo.BookingStatistics;
import com.example.booking_service.repository.BookingStatisticsRepository;
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
public class KafkaBookingRoomListener {

    private final BookingStatisticsRepository bookingStatisticsRepository;

    @KafkaListener(topics = "${app.kafka.kafkaBookingTopic}",
            groupId = "${app.kafka.kafkaBookingGroupId}",
            containerFactory = "kafkaBookingRoomEventConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload BookingRoomEvent event,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        UUID uuid = UUID.randomUUID();

        bookingStatisticsRepository.save(new BookingStatistics(
                uuid.toString(),
                event.getUserId(),
                event.getRoomId().toString(),
                event.getArrival(),
                event.getDeparture(),
                LocalDateTime.now().toString()
                ));
    }
}
