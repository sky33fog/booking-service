package com.example.booking_service.configuration;

import com.example.booking_service.model.kafka.BookingRoomEvent;
import com.example.booking_service.model.kafka.CreateUserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.kafkaUserGroupId}")
    private String kafkaUserGroupId;

    @Value("${app.kafka.kafkaBookingGroupId}")
    private String kafkaBookingGroupId;

    @Bean
    public ProducerFactory<String, CreateUserEvent> kafkaCreateUserEventProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, CreateUserEvent> kafkaTemplateCreateUser(ProducerFactory<String, CreateUserEvent> kafkaCreateUserEventProducerFactory) {
        return new KafkaTemplate<>(kafkaCreateUserEventProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, CreateUserEvent> kafkaCreateUserEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaUserGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateUserEvent> kafkaCreateUserEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, CreateUserEvent> kafkaCreateUserEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CreateUserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaCreateUserEventConsumerFactory);

        return  factory;
    }


    @Bean
    public ProducerFactory<String, BookingRoomEvent> kafkaBookingRoomEventProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, BookingRoomEvent> kafkaTemplateBookingRoom(ProducerFactory<String, BookingRoomEvent> kafkaBookingRoomEventProducerFactory) {
        return new KafkaTemplate<>(kafkaBookingRoomEventProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, BookingRoomEvent> kafkaBookingRoomEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaUserGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingRoomEvent> kafkaBookingRoomEventConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, BookingRoomEvent> kafkaBookingRoomEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, BookingRoomEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaBookingRoomEventConsumerFactory);

        return  factory;
    }
}
