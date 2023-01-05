package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "category")
public class Category {

    @TableId(value = "name")
    private String name;

    private String description;
}
