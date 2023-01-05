package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "orderitem")
public class OrderItem {

    @TableField("order_id")
    private String orderId;

    @TableField("product_id")
    private String productId;

    private int buynum;
}
