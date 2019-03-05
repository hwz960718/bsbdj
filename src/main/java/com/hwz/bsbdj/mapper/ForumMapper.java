package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.Forum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumMapper {
    int deleteByPrimaryKey(Long forumId);

    int insert(Forum record);

    int insertSelective(Forum record);

    Forum selectByPrimaryKey(Long forumId);

    int updateByPrimaryKeySelective(Forum record);

    int updateByPrimaryKeyWithBLOBs(Forum record);

    int updateByPrimaryKey(Forum record);
}