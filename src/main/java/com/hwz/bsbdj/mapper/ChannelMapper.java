package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.Channel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChannelMapper {
    int deleteByPrimaryKey(Integer channelId);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Integer channelId);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
}