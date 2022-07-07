package com.smartinsight.datasource.datasource;

import com.smartinsight.datasource.utils.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 自定义的数据源对象 内部持有多个数据源 在执行sql的时候动态选择其中一个数据源
 * 常用于mysql主从架构的访问
 * 此处用来同步两个数据库的数据
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 构造方法 用于构造一个多数据源的map
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 多数据源内存中的数据结构是一个map
     * key ：自定义  value ：datasource对象
     * 此方法用与在执行sql的时候动态获取一个map中的key 以便取到某一个数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
