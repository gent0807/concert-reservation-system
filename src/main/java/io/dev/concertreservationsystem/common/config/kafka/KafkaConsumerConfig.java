package io.dev.concertreservationsystem.common.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value( "${kafka.consumer.group-id}")
    private String CONSUMER_GROUP_ID;

    @Value( "${spring.kafka.consumer.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {

        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(KafkaTopicKey.POINT_USE_HISTORY_CREATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.CONCERT_BASIC_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.CONCERT_DETAIL_STATUS_UPDATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.SEAT_STATUS_UPDATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.RESERVATION_CREATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.RESERVATION_STATUS_UPDATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.PAYMENT_CREATE_EVENT).partitions(3).replicas(1).build(),
                TopicBuilder.name(KafkaTopicKey.PAYMENT_STATUS_UPDATE_EVENT).partitions(3).replicas(1).build()
        );

    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);  // Kafka 주소
        properties.put(GROUP_ID_CONFIG, CONSUMER_GROUP_ID);  // Consumer 들을 그룹으로 묶을 수 있다.
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        kafkaListenerContainerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return kafkaListenerContainerFactory;
    }
}
