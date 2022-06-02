package com.atguigu.controller;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;

    @GetMapping("auth/follow/{houseId}")
    public Result addFollow(@PathVariable("houseId") Long houseId, HttpSession session){
        //判断用户之前是否添加了这条数据的关注,根据用户ID和房源ID查询UserFollow
        //1.获取当前登入的用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), houseId);
        //2.如果用户之前已经添加过关注，那么我们只需要更新这条数据的is_deleted为0

        if (userFollow != null) {
            //说明用户之前关注过
            userFollow.setIsDeleted(0);
            //更新关注
            userFollowService.update(userFollow);
        }else {
            //3. 如果用户之前没有添加过关注，我们需要新增一条数据
            userFollow = new UserFollow();
            userFollow.setUserId(userInfo.getId());
            userFollow.setHouseId(houseId);
            userFollowService.insert(userFollow);
        }

        return Result.ok();
        }
    }

