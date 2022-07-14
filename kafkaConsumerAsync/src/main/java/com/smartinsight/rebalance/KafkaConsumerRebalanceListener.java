package com.smartinsight.rebalance;

import com.smartinsight.consumer.CustomConsumer;
import com.smartinsight.worker.ProcessingWindow;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerRebalanceListener implements ConsumerRebalanceListener {
    /**
     * 失去分区时执行的方法
     */
    @Override
    @SneakyThrows
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        Map<String, ProcessingWindow> offsetMap = CustomConsumer.offsetMap;
        for (TopicPartition topicPartition : collection) {
            ProcessingWindow window = offsetMap.get(String.valueOf(topicPartition.partition()));
            if (window == null) {
                return;
            } else {
                // 杀死线程
                window.exit = true;
                Thread.sleep(500);
                // 最后再提交一次偏移量
                Map<TopicPartition, OffsetAndMetadata> curOffset = new HashMap<>();
                TopicPartition tp = new TopicPartition(window.getTopic(), window.getPartition());
                OffsetAndMetadata offset = new OffsetAndMetadata(window.getLastCommitOffset());
                curOffset.put(tp, offset);
                window.getConsumer().commitSync(curOffset);
                // 移除任务
                offsetMap.remove(String.valueOf(topicPartition.partition()));
            }
        }

    }

    /**
     * 获取新分区时执行的方法
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        // 不用执行
    }
}
