package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    List<Map<String,Object>> findZnodes(Long id);

    /**
     *根据dictCode查询dict集合
     * @param parentDictCode
     * @return
     */
    List<Dict> findDictListByParentDictCode(String parentDictCode);

    /**
     * 根据父节点的id查询其所有的子节点
     * @param parentId
     * @return
     */
    List<Dict> findDictListByParentId(Long parentId);
}
