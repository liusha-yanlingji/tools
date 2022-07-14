package com.smartinsight.worker;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.BlockingQueue;

/**
 * 工作线程
 */
@Slf4j
public class WorkerThread extends Thread {
    /**
     * 任务队列
     */
    private BlockingQueue<ConsumerRecord<String, String>> queue;
    /**
     * 已完成的任务
     */
    private ProcessingWindow window;

    public WorkerThread(BlockingQueue<ConsumerRecord<String, String>> queue, ProcessingWindow window) {
        this.queue = queue;
        this.window = window;
    }

    @SneakyThrows // 如果阻塞方法take响应中断 则直接捕获异常不处理 直接再次take
    @Override
    public void run() {
        if (window == null || queue == null) {
            return;
        }
        // 是否终止线程
        while (!window.exit) {
            ConsumerRecord<String, String> record = queue.take();
            try {
                hander(record);
                commitOffset(record);
            } catch (Exception e) {
                // 如果发生异常先记录下来 并且跳过这个任务 避免影响任务队列已完成任务提交
                log.error("kafka消费异常：" + record.value(), e);
            } finally {
                commitOffset(record);
            }
        }
    }

    /**
     * 消费消息
     */
    private void hander(ConsumerRecord<String, String> record) {
        String value = record.value();
        System.out.println("消费消息" + value);
    }

    /**
     * 本地提交偏移量
     */
    @SneakyThrows
    private void commitOffset(ConsumerRecord<String, String> record) {
        long offset = record.offset();
        // 如果是下一轮的提交请求先阻塞
        while (offset - window.getLastCommitOffset() >= window.getSize()) {
            Thread.sleep(10);
        }
        window.putOffset(offset);
    }
}
