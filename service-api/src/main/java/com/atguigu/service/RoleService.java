package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.service
 *
 * @author Leevi
 * 日期2022-05-17  14:13
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    /**
     * 查询用户的角色列表
     * @param adminId
     * @return
     */
    Map<String,List<Role>>findRoleIdListByAdminId(Long adminId);

    void removeAdminRole(Long adminId,List<Long> roleIds);

    /**
     * 保存用户角色
     * @param adminId
     * @param roleIds
     */
    void saveAdminRole(Long adminId, List<Long> roleIds);

}
