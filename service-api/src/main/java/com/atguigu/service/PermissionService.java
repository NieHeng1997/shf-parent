package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    List<Map<String,Object>>findPermissionByRoleId(Long roleId);

    /**
     * 保存权限角色信息
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermission(Long roleId,List<Long> permissionIdList);

    /**
     * 查询用户的所有权限
     * @param adminId
     * @return
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission>findAllMenu();
}
