package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper {
    /**
     * 根据用户id和房源id查询关注信息
     * @param userId
     * @param houseId
     * @return
     */
    UserFollow findByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    /**
     * 更新房源关注信息
     * @param userFollow
     */
    void update(UserFollow userFollow);

    /**
     * 新增关注信息
     * @param userFollow
     */
    void insert(UserFollow userFollow);
}
