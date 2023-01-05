package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)//指定自增策略
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private Integer usertype;

}
