package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_EDIT = "admin/edit";
    private final static  String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";

    @RequestMapping()
    public String index(@RequestParam Map<String,Object> filters, Model model){
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }
    @PostMapping("/save")
    public String save(Admin admin, Model model){
        adminService.insert(admin);
        return successPage(model,"新增用户成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Admin admin,Model model){
        adminService.update(admin);
        return successPage(model,"編輯用戶成功");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable Long id,Model model){
        model.addAttribute("id",id);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile)throws IOException {
        //id是用户的id
        //将图片上传到七牛云,生成一个唯一的文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String uuidName = FileUtil.getUUIDName(originalFilename);
        QiniuUtils.upload2Qiniu(multipartFile.getBytes(), uuidName);
        //将图片信息保存到数据库
        //获取图片URL
        String headUrl = QiniuUtils.getUrl(uuidName);
        //封装信息到Admin
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(headUrl);
        //更新admin
        adminService.update(admin);
        //重定向访问admin的首页
        return LIST_ACTION;
    }

    @GetMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable("adminId") Long adminId , Model model){
        Map<String , List<Role>>roleMap = roleService.findRoleIdListByAdminId(adminId);
        model.addAllAttributes(roleMap);
        return PAGE_ASSIGN_SHOW;
    }

    @PostMapping("/assignRole")
    public String assignRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") List<Long> roleIds,
                             Model model){
        roleService.saveAdminRole(adminId,roleIds);
        return successPage(model,"保存角色成功");
    }


}
