package com.norato.easymall.dto;

import lombok.Data;

@Data
public class CartInfo {

    private Integer cartID;

    private String pid;

    private String name;

    private String imgurl;

    private Double price;

    private Integer num;
}
