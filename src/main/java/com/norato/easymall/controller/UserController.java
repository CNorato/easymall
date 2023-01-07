package com.norato.easymall.controller;


import com.norato.easymall.dto.result.Result;
import com.norato.easymall.dto.result.UsernameAndIdResult;
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

    @Operation(summary = "查询用户名是否可用")
    @GetMapping("/checkUser")
    public Result checkUser(String username) {
        Result result = new Result();
        if (userService.login(username) == null) {
            return result.success().msg("用户名可用");
        }
        return result.fail().msg("用户名已存在");
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public Result regist(User user) {
        Result result = new Result();
        if (userService.login(user.getUsername()) != null) {
            return result.fail().msg("用户名已存在");
        }
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userService.register(user) > 0) {
            return result.success();
        }
        return result.fail();
    }

    @Operation(summary = "查询用户ID")
    @GetMapping("/query")
    public UsernameAndIdResult query(String username) {
        UsernameAndIdResult result = new UsernameAndIdResult();
        User user1 = userService.login(username);
        if (user1 != null) {
            return result.success()
                    .msg("查询成功")
                    .username(user1.getUsername())
                    .userId(user1.getId());
        }
        return result.fail();
    }

}
