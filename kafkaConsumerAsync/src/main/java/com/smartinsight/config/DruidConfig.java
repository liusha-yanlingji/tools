package com.smartinsight.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * druid 配置多数据源
 */
@Configuration
public class DruidConfig {
    /**
     * 目标数据库2
     */
    @Bean(name = "RES")
    @ConfigurationProperties("spring.datasource.druid.res")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.res", name = "enabled", havingValue = "true")
    @Primary
    public DataSource target2DataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }
}
