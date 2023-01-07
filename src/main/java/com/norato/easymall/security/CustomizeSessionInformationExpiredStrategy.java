package com.norato.easymall.security;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.result.Result;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        Result result = new Result().fail().msg("您的账号已在其他地方登录");
        event.getResponse().setContentType("application/json;charset=utf-8");
        event.getResponse().getWriter().write(JSONObject.toJSONString(result));
    }
}
