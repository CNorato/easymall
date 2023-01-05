package com.norato.easymall.service;


import com.norato.easymall.entity.User;

import java.util.List;

public interface UserService {

	public User login(String username);

	public int register(User user);

	public User selectById(Integer userId);

	public List<User> selectAll();

	public int update(User user);
}
