package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 * @author Leevi
 * 日期2022-05-23  10:27
 */
public interface HouseImageMapper extends BaseMapper<HouseImage> {
    /**
     * 根据房源id和type查询房源的图片列表
     * @param houseId
     * @param type
     * @return
     */
    List<HouseImage> findHouseImageList(@Param("houseId") Long houseId, @Param("type") Integer type);
}
