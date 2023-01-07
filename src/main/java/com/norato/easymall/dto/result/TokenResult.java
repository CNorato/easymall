package com.norato.easymall.dto.result;

import lombok.Data;

@Data
public class TokenResult {
    private Integer status;

    private String msg;

    private String token;

    public TokenResult success() {
        setStatus(200);
        return this;
    }

    public TokenResult fail() {
        setStatus(500);
        return this;
    }

    public TokenResult msg(String msg) {
        setMsg(msg);
        return this;
    }

    public TokenResult token(String token) {
        setToken(token);
        return this;
    }
}
