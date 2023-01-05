package com.norato.easymall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.norato.easymall.entity.User;
import com.norato.easymall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public User login(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);    //是否查询到对象
    }

    @Override
    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectList(null);
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }

    @Override
	public int register(User user) {
        return userMapper.insert(user);
	}
}


