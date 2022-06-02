package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;

/**
 * 包名:com.atguigu.mapper
 *
 * @author Leevi
 * 日期2022-05-21  15:40
 */
public interface HouseMapper extends BaseMapper<House> {
    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
