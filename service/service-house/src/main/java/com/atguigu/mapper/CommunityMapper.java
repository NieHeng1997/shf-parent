package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 * @author Leevi
 * 日期2022-05-21  11:11
 */
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> findAll();
}
