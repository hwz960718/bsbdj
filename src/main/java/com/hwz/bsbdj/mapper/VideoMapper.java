package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Long videoId);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long videoId);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}