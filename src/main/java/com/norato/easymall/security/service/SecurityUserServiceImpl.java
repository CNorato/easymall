package com.norato.easymall.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.norato.easymall.entity.User;
import com.norato.easymall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String role = user.getUsertype() == 0 ? "admin" : "user";
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        List<GrantedAuthority> authorities = List.of(grantedAuthority);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
