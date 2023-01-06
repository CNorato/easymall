package com.norato.easymall.service;


import com.norato.easymall.entity.User;

import java.util.List;

public interface UserService {

	User login(String username);

	int register(User user);

	User selectById(Integer userId);

	List<User> selectAll();

	int update(User user);
}
