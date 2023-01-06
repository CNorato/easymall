package com.norato.easymall.service;


import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;

import java.util.List;

public interface OrderService {

    void addOrder(String cartIds, Order myOrder);

    List<Order> findOrderByUserId(Integer userId);

    List<Order> findOrderByPaystate(Integer paystate);

    List<OrderItem> orderitem(String order_id);

    List<OrderItem> prodsale();

    void payorder(String id);

    void sendorder(String id);

    void delorder(String id);


}
