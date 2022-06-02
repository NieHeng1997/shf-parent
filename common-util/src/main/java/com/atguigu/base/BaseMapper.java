package com.atguigu.base;

import com.github.pagehelper.Page;


import java.util.Map;

public interface BaseMapper<T> {
    /**
     * 添加角色信息
     */
    void insert(T t);

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 修改角色信息
     */
    void update(T t);

    /**
     *删除角色信息
     * @param id
     */
    void delete(Long id);

    /**
     *分页
     * @param filters
     * @return
     */
    Page<T> findPage(Map<String,Object> filters);
}

