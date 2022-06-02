package com.atguigu.mapper;

import com.github.pagehelper.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-service.xml")
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void findPage() {
        Map map = new HashMap();
        Page page = roleMapper.findPage(map);
        System.out.println(page);
    }
}