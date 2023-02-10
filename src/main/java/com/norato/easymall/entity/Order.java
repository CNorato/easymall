package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName(value = "orders")
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private Double money;

    private String receiverinfo;

    private Integer paystate;

    private Timestamp ordertime;

    @TableField("user_id")
    private Integer userId;
}
