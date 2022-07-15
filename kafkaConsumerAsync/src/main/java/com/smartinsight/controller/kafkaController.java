package com.smartinsight.controller;

import com.smartinsight.rebalance.KafkaConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class kafkaController {
    @Resource
    KafkaTemplate<String, String> KafkaTemplate;

    @GetMapping("produce")
    public void produce() {
        for (int i = 0; i < 10; i++) {
            KafkaTemplate.send("test", String.valueOf(i));
        }
    }

    /**
     *
     */
    @GetMapping("rebalance")
    public void rebalance() {
        // 模拟kafka再均衡
        KafkaConsumerRebalanceListener kl = new KafkaConsumerRebalanceListener();
        Collection<TopicPartition> collection = new ArrayList<>();
        TopicPartition TopicPartition = new TopicPartition("test", 0);
        collection.add(TopicPartition);
        kl.onPartitionsRevoked(collection);
    }
}
