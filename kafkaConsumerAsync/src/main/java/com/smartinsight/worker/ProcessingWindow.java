package com.smartinsight.worker;

import lombok.Data;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 已完成的任务的滑动窗口
 */
@Data
public class ProcessingWindow {
    // 主题
    private String topic;
    // 分区
    private int partition;
    // 消费者
    Consumer<String, String> consumer;

    // 任务队列
    private BlockingQueue<ConsumerRecord<String, String>> queue;

    // 上次提交的偏移量
    private long lasttimeOffset;
    public volatile boolean exit = false; // 线程终止标识
    private int size;// offsets数组长度
    private long[] offsets;// 偏移量数组 用来暂存已消费完成的kafka消息的偏移量
    private long lastCommitOffset;// 最后一次提交的偏移量

    public static ProcessingWindow init(String topic, int partition, long offset,Consumer<String, String> consumer) {
        ProcessingWindow pw = new ProcessingWindow();
        pw.setTopic(topic);
        pw.setPartition(partition);
        pw.setConsumer(consumer);
        pw.setLastCommitOffset(offset);
        pw.setSize(200);
        pw.setOffsets(new long[200]);
        pw.setQueue(new ArrayBlockingQueue<>(200));
        return pw;
    }

    /**
     * 提交偏移量方法
     */
    public synchronized void putOffset(long offset) {
        if (offset < lastCommitOffset) {
            return;
        }
        // 本偏移量在数组中的下标
        int index = (int) (offset % size - 1);
        if (offset != lastCommitOffset) {
            offsets[index] = offset;
        } else {
            while (offset == lastCommitOffset) {
                // 提交偏移量
                lastCommitOffset = offset + 1;
                // 取数组中的下一个数字
                offset = next(index);
                index = (int) (offset % size - 1);
            }
        }
    }

    /**
     * 找数组中的下一个下标的元素
     */
    private long next(int index) {
        // 如果当前是最后一个元素 返回第一个
        if (index == size - 1) {
            return offsets[0];
        }
        return offsets[index + 1];
    }
}
