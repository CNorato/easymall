package com.norato.easymall.service;


import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.SaleProduct;
import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;

import java.util.List;

public interface OrderService {

    void addOrder(List<CartInfo> carts, Order myOrder);

    List<Order> findOrderByUserId(Integer userId);

    List<Order> findOrderByPaystate(Integer paystate);

    List<OrderItem> orderitem(String order_id);

    List<OrderItem> prodsale();

    void payorder(String id);

    void sendorder(String id);

    void delorder(String id);

    List<OrderInfo> findOrderInfoByUserId(Integer userId);

    List<SaleProduct> findSaleProduct();

    List<OrderInfo> findOrderInfoByPaystate(Integer paystate);
}
