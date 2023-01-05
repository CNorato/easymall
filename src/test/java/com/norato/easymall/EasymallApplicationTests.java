package com.norato.easymall;

import com.norato.easymall.entity.User;
import com.norato.easymall.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class EasymallApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void encryptPassword() {
        List<User> users = userService.selectAll();

        for (User user : users) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.update(user);
        }
    }

}
