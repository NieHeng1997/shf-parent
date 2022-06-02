package com.atguigu.service;

import com.atguigu.entity.UserFollow;

public interface UserFollowService {
    /**
     * 根据用户id和房源id查询关注信息
     * @param userId
     * @param houseId
     * @return
     */
    UserFollow findByUserIdAndHouseId(Long userId,Long houseId);

    /**
     * 更新房源的关注信息
     * @param userFollow
     */
    void update(UserFollow userFollow);

    /**
     * 新增关注信息
     * @param userFollow
     */
    void insert(UserFollow userFollow);

}
