package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}