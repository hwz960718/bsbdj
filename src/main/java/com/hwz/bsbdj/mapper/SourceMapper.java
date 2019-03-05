package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.Source;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SourceMapper {
    int deleteByPrimaryKey(Long sourceId);

    int insert(Source record);

    int insertSelective(Source record);

    Source selectByPrimaryKey(Long sourceId);

    int updateByPrimaryKeySelective(Source record);

    int updateByPrimaryKey(Source record);
}