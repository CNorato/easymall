package com.norato.easymall.dto.result;

import lombok.Data;

@Data
public class Result {
    private Integer status;

    private String msg;

    private Object data;

    public Result success() {
        setStatus(200);
        return this;
    }

    public Result fail() {
        setStatus(500);
        return this;
    }

    public Result msg(String msg) {
        setMsg(msg);
        return this;
    }

    public Result data(Object data) {
        setData(data);
        return this;
    }
}
