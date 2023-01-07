package com.norato.easymall.security;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.result.TokenResult;
import com.norato.easymall.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录成功处理
 * */

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String requestURI = request.getRequestURI();
        TokenResult result = new TokenResult();
        User user = (User) authentication.getPrincipal();
        if (requestURI.contains("admin")) {
            GrantedAuthority admin = new SimpleGrantedAuthority("admin");
            boolean isAdmin = authentication.getAuthorities().contains(admin);
            if (isAdmin) {
                result.success().msg("登录成功")
                        .token(JwtTokenUtil.createToken(user.getUsername(), "admin"));
            } else {
                result.fail().msg("非管理员用户");
            }
        } else {
            result.success().msg("登录成功")
                    .token(JwtTokenUtil.createToken(user.getUsername(), "user"));
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
