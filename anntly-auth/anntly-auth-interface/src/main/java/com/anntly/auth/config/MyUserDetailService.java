package com.anntly.auth.config;

import com.anntly.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author soledad
 * @Title: MyUserDetailService
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2022:27
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findUserByUsername(username);
    }
}
