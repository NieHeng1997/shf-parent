package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserFollow;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.mapper.UserInfoMapper;
import com.atguigu.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl implements UserFollowService  {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Transactional(propagation = Propagation.SUPPORTS)

    @Override
    public UserFollow findByUserIdAndHouseId(Long userId, Long houseId) {
        return userFollowMapper.findByUserIdAndHouseId(userId,houseId);
    }

    @Override
    public void update(UserFollow userFollow) {
        userFollowMapper.update(userFollow);
    }

    @Override
    public void insert(UserFollow userFollow) {
        userFollowMapper.insert(userFollow);
    }
}
