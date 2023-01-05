package com.norato.easymall.security;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.utils.JwtTokenUtil;
import jakarta.servlet.ServletException;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        if (requestURI.contains("admin")) {
            GrantedAuthority admin = new SimpleGrantedAuthority("admin");
            boolean isAdmin = authentication.getAuthorities().contains(admin);
            if (isAdmin) {
                json.put("status", 200);
                json.put("token", JwtTokenUtil.createToken(user.getUsername(), "admin"));
            } else {
                json.put("status", 500);
                json.put("message", "非管理员用户");
            }
        } else {
            json.put("status", 200);
            json.put("token", JwtTokenUtil.createToken(user.getUsername(), "user"));
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json.toJSONString());
    }
}
