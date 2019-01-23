package com.anntly.dish.mapper;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author soledad
 * @Title: CategoryMapperTest
 * @ProjectName anntly
 * @Description:
 * @date 2019/1/1910:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test01(){
        List<Long> ids = Arrays.asList(1L,2L,3L,4L,5L,6L,7L);
        int i = categoryMapper.updateBatch(ids);
        assertNotEquals(0,i);
    }
}
