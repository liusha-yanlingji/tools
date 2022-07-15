package com.smartinsight.worker;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
        log.debug("初始化一个线程");
    }

    @SneakyThrows // 如果阻塞方法take响应中断 则直接捕获异常不处理 直接再次take
    @Override
    public void run() {
        if (window == null || queue == null) {
            return;
        }

        while (true) {
            ConsumerRecord<String, String> record = queue.poll(500, TimeUnit.MILLISECONDS);
            System.out.println(window.exit + "终止状态" + Thread.currentThread().getName());
            // 是否终止线程
            if (window.exit) {
                System.out.println("我被终止了" + Thread.currentThread().getName());
                return;
            }
            try {
                if (record == null) {
                    continue;
                }
                log.debug(Thread.currentThread().getName() + " : 从队列取出一个消息" + record.offset());
                hander(record);
            } catch (Exception e) {
                // 如果发生异常先记录下来 并且跳过这个任务 避免影响任务队列已完成任务提交
                log.error("kafka消费异常：" + record.value(), e);
            } finally {
                if (record != null) {
                    commitOffset(record);
                }
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
        log.debug(Thread.currentThread().getName() + " : 提交偏移量" + offset + " -> " + Arrays.toString(window.getOffsets()));
    }
}
