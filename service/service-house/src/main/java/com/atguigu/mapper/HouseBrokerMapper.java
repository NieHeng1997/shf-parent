package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 * @author Leevi
 * 日期2022-05-23  10:32
 */
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    /**
     * 根据房源id查询房源经纪人列表
     * @param houseId
     * @return
     */
    List<HouseBroker> findHouseBrokerListByHouseId(Long houseId);
}
