package com.smartinsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目说明
 * kafka 多线程异步消费
 * 当一个任务完成时 如果他之前的任务未完成则先提交到内存保存
 * 当他之前的任务都完成时，则一起提交偏移量 类似一个滑动窗口 这个窗口一直往前走 直到遇见未完成的任务则先暂停
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
