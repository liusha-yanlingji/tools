package com.smartinsight.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartinsight.model.FullText;
@Mapper
public interface FullTextMapper extends BaseMapper<FullText>{

}
