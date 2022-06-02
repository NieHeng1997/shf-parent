package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
       //查询所有权限
        List<Permission> allpermissionList = permissionMapper.findAll();
        //查询当前角色已分配的权限ID
        List<Long> assignPermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //创建一个List用于储存返回数据
        ArrayList<Map<String,Object>> permissionList = new ArrayList<>();
        //遍历所有权限信息
        for (Permission permission : allpermissionList) {
            //创建一个Map用于存储返回数据
            HashMap<String, Object> map = new HashMap<>();
            //判断当前权限是否分配
            if(allpermissionList.contains(permission.getId())){
                //已分配
                map.put("checked",true);
            }else{
                //未分配
                map.put("checked",false);
            }
            //设置id
            map.put("id",permission.getId());
            //设置pId
            map.put("pId",permission.getParentId());
            //设置name
            map.put("name",permission.getName());
            //设置open
            map.put("open",true);
            //将map添加到List中
            permissionList.add(map);
        }
        return permissionList;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIdList) {
        //查询当前角色的所有permissionId
        List<Long> rolePermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //找出要移除的permissionId
        List<Long> removePermissionIdList = rolePermissionIdList.stream()
                .filter(item -> !permissionIdList.contains(item))
                .collect(Collectors.toList());
        //删除角色权限
        if (removePermissionIdList != null && removePermissionIdList.size() > 0) {
            rolePermissionMapper.removeRolePermission(roleId,removePermissionIdList);
        }

        //给角色添加权限
        for (Long permissionId : permissionIdList) {
            //根据roleId和permissionId查询角色权限信息
            RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionId(roleId,permissionId);
            //判断当前roleId和permissionId是否存在关联
            if (rolePermission == null) {
                rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                rolePermissionMapper.insert(rolePermission);
            }else {
                if (rolePermission.getIsDeleted() == 1){
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            }
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission>permissionList = null;
        //判断是否是超级管理员
        if(adminId == 1){
            //为超级管理员
            permissionList = permissionMapper.findAll();
        }else{
            //根据adminId查询权限列表
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissionList = permissionMapper.findAll();
        return PermissionHelper.build(permissionList);
    }


}
