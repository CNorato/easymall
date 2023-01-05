package com.norato.easymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.entity.User;
import com.norato.easymall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Operation(summary = "查询用户名是否存在")
    @GetMapping("/checkUser")
    public JSONObject checkUser(String username) {
        JSONObject json = new JSONObject();
        if (userService.login(username) == null) {
            json.put("status", 200);
        } else {
            json.put("status", 500);
        }
        return json;
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public JSONObject regist(String username, String password, String nickname, String email) {
        JSONObject json = new JSONObject();
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        if (userService.register(user) > 0) {
            json.put("status", 200);
        } else {
            json.put("status", 500);
        }
        return json;
    }

    @Operation(summary = "查询用户ID")
    @GetMapping("/query")
    public JSONObject query(String username) {
        System.out.println(username);
        System.out.println("\n\n\n\n");
        User user1 = userService.login(username);
        JSONObject json = new JSONObject();
        if (user1 != null) {
            json.put("status", 200);
            json.put("username", user1.getUsername());
            json.put("userId", user1.getId());
        } else {
            json.put("status", 500);
        }
        return json;
    }

}
