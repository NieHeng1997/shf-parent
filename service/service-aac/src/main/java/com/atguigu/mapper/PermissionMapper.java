package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

  List<Permission>findAll();

  /**
   * 查询用户的菜单权限列表
   * @param adminId
   * @return
   */
  List<Permission>findPermissionListByAdminId(Long adminId);

  List<Permission>findAllMenu();
}
