package com.smartinsight.consumer;

import com.smartinsight.worker.ProcessingWindow;
import com.smartinsight.worker.WorkerThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class CustomConsumer {
    public static Map<String, ProcessingWindow> offsetMap = new HashMap<>();// key是分区

    @KafkaListener(topics = {"test"}, groupId = "user")
    public void listen(List<ConsumerRecord<String, String>> records, Consumer<String, String> consumer) throws InterruptedException {
        // 分派任务
        for (ConsumerRecord<String, String> record : records) {
            String partition = String.valueOf(record.partition());
            ProcessingWindow processingWindow = offsetMap.get(partition);
            if (processingWindow == null) {
                // 初始化一个滑动窗口
                ProcessingWindow window = ProcessingWindow.init(record.topic(), record.partition(), record.offset(), consumer);
                offsetMap.put(partition, window);
                // 初始化10个线程
                for (int i = 0; i < 10; i++) {
                    WorkerThread WorkerThread = new WorkerThread(window.getQueue(), window);
                    WorkerThread.start();
                }
            } else {
                // 分配任务
                BlockingQueue<ConsumerRecord<String, String>> queue = processingWindow.getQueue();
                // 任务已满 阻塞拉取线程
                queue.put(record);
            }
        }
        // 提交偏移量
        for (ProcessingWindow window : offsetMap.values()) {
            // 如果偏移量没有变化 则不提交
            long lastCommitOffset = window.getLastCommitOffset();
            if (lastCommitOffset <= window.getLasttimeOffset()) {
                return;
            }
            // 构造要提交的偏移量
            Map<TopicPartition, OffsetAndMetadata> curOffset = new HashMap<>();
            TopicPartition tp = new TopicPartition(window.getTopic(), window.getPartition());
            OffsetAndMetadata offset = new OffsetAndMetadata(lastCommitOffset);
            curOffset.put(tp, offset);
            consumer.commitSync(curOffset);
            // 记录一下
            window.setLasttimeOffset(lastCommitOffset);
        }
    }
}
