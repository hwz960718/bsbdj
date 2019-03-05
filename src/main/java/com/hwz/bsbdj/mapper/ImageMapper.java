package com.hwz.bsbdj.mapper;

import com.hwz.bsbdj.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    int deleteByPrimaryKey(Long imageId);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Long imageId);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}