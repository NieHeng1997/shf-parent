package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface BaseService<T> {
    /**
     * 保存角色信息
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
     *
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param filters
     * @return
     */
    PageInfo<T> findPage(Map<String,Object> filters);
}