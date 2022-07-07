package com.smartinsight.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.smartinsight.datasource.datasource.DynamicDataSource;
import com.smartinsight.datasource.enums.DataSourceType;
import com.smartinsight.datasource.properties.DruidProperties;
import com.smartinsight.datasource.utils.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 */
@Configuration
public class DruidConfig {
    /**
     * 源数据库
     */
    @Bean(name = "SUANFA")
    @ConfigurationProperties("spring.datasource.druid.suanfa")
    public DataSource sourceDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 目标数据库1
     */
    @Bean(name = "RESDEV")
    @ConfigurationProperties("spring.datasource.druid.resdev")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.resdev", name = "enabled", havingValue = "true")
    public DataSource target1DataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 目标数据库2
     */
    @Bean(name = "RES")
    @ConfigurationProperties("spring.datasource.druid.res")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.res", name = "enabled", havingValue = "true")
    public DataSource target2DataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 算法库
        targetDataSources.put(DataSourceType.SUANFA.name(), SpringUtils.getBean(DataSourceType.SUANFA.name()));
        // 票据池开发库 & 默认库
        DataSource bean = SpringUtils.getBean(DataSourceType.RESDEV.name());
        targetDataSources.put(DataSourceType.RESDEV.name(), bean);
        // 票据池演示库
        targetDataSources.put(DataSourceType.RES.name(), SpringUtils.getBean(DataSourceType.RES.name()));
        return new DynamicDataSource(bean, targetDataSources);
    }
}
