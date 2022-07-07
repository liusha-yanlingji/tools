package com.smartinsight.datasource.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多数据源key包装类
 * 用于存放多数据源的key
 * 解释 当前线程执行sql的时候会选择一个数据源 也就是选择一个数据源的key 这个key就保存在这里
 * 可以手动在代码里把key放进来 注意线程结束前要把key删除 不然该线程被回收后 其他任务复用该线程时还会用这个key 可能就会报错
 */
public class DynamicDataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType) {
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
