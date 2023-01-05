package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @TableId(value = "cartID", type = IdType.AUTO)
    private Integer cartId;

    private Integer user_id;

    private String pid;

    private Integer num;
}
