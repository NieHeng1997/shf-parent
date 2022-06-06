package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.controller
 *
 * @author Leevi
 * 日期2022-05-21  09:28
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    @GetMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        //调用业务层的方法查询数据
        System.out.println("本次改动，模拟代码冲突");
        System.out.println("入参数：" + id);
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        System.out.println("-------------------");
        return Result.ok(znodes);
    }

    /**
     * 根据父节点的id获取子节点数据列表
     * @param parentId
     * @return
     */
    @RequestMapping("/findDictListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId) {
        List<Dict> dictList = dictService.findDictListByParentId(parentId);
        return Result.ok(dictList);
    }
}
