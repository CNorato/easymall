package com.norato.easymall.dto;

import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.Product;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderInfo {

    private Order order;//订单信息

    private Map<Product, Integer> map;//该订单中的所有订单项信息

    private List<Product> productsList;

}
