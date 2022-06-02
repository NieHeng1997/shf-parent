package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    private static final String PAGE_INDEX = "frame/index";
    @GetMapping("/")
    public String index(Model model){
        //因为现在还未引入权限认证框架，没有登录，所以现在先将用户的id写死，等集成了权限认证框架之后，再获取当前登录的用户
        Long adminId = 1L;
        Admin admin = adminService.getById(adminId);
        //查询用户的权限列表
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(adminId);
        model.addAttribute("admin",admin);
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }
}
