package com.norato.easymall.security;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 权限不足
 * */

@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        Result result = new Result().fail().msg("权限不足");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
