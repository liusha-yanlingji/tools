package com.smartinsight.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomConsumer {

    @KafkaListener(topics = {"test"}, groupId = "user")
    public void listen(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {

    }
}
