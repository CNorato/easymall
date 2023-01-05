package com.norato.easymall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "products")
public class Product {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String name;

    private Double price;

    private String category;

    private Integer pnum;

    private Integer soldnum;

    private String imgurl;

    private String description;

    private Integer status;
}
