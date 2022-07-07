package com.smartinsight.testcontroller;

import com.smartinsight.datasource.aspectj.DataSource;
import com.smartinsight.datasource.enums.DataSourceType;
import com.smartinsight.datasource.utils.DynamicDataSourceContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    /**
     * service 选择数据库示例
     */
    @DataSource(value = DataSourceType.RESDEV)
    public String getFullbyId() {
        return "1";
    }

    /**
     * 代码中切换数据库示例
     */
    public String getFullbyName() {
        try {
            // 选取数据源
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RESDEV.name());
            // 执行dao
        } finally {
            // 销毁数据源
            DynamicDataSourceContextHolder.clearDataSourceType();
        }

        try {
            // 切换到另一个数据源
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RES.name());
            // 执行dao
        } finally {
            // 销毁数据源
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return "";
    }
}
