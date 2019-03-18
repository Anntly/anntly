package com.anntly.user.mapper;


import com.anntly.user.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author soledad
 * @Title: UserMapperTest
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/3/1710:17
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        User rs = userMapper.findUserByUsername("rs");
        System.out.println(rs);
    }
}
