package com.norato.easymall.dto.result;

import lombok.Data;

@Data
public class UsernameAndIdResult {

    private Integer status;

    private String msg;

    private String username;

    private Integer userId;

    public UsernameAndIdResult success() {
        setStatus(200);
        return this;
    }

    public UsernameAndIdResult fail() {
        setStatus(500);
        return this;
    }

    public UsernameAndIdResult msg(String msg) {
        setMsg(msg);
        return this;
    }

    public UsernameAndIdResult username(String username) {
        setUsername(username);
        return this;
    }

    public UsernameAndIdResult userId(Integer userId) {
        setUserId(userId);
        return this;
    }

}
