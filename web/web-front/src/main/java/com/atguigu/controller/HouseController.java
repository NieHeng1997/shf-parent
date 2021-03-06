package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryBo houseQueryBo,
                               @PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize){
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryBo);
        return Result.ok(pageInfo);
    }
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private UserFollowService userFollowService;
    @Reference
    private HouseUserService houseUserService;


    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){
        //1. 根据房源id获取房源信息
        House house = houseService.getById(id);
        //2.根据小区id获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //3.根据房源id查询房源经纪人列表
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        //4.根据房源id查询房源图片列表
        List<HouseImage> houseImageList = houseImageService.findHouseImageList(id, 1);
        //5.根据房源id查询房东列表
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        //6.查询当前用户是否已关注该房源
        //6.1 获取当前用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        boolean isFollow = false;
        //当然:不一定登录了，所以我们要判断userInfo是否为空
        if (userInfo != null) {
            //判断当前用户是否已关注当前房源
            //调用UserFollowService的方法查询用户是否已关注房源
            UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), id);
            if (userFollow != null && userFollow.getIsDeleted() == 0) {
                //已关注
                isFollow= true;
            }
        }
        Map resultMap = new HashMap();
        resultMap.put("house",house);
        resultMap.put("community",community);
        resultMap.put("houseBrokerList",houseBrokerList);
        resultMap.put("houseImage1List",houseImageList);
        resultMap.put("houseUserList",houseUserList);
        resultMap.put("isFollow",isFollow);

        //7. 封装数据进行响应
        return Result.ok(resultMap);
    }
}
