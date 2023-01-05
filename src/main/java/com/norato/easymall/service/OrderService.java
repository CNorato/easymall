package com.norato.easymall.service;


import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;

import java.util.List;

public interface OrderService {

    public void addOrder(String cartIds, Order myOrder);

    public List<Order> findOrderByUserId(Integer userId);

    public List<Order> findOrderByPaystate(Integer paystate);

    public List<OrderItem> orderitem(String order_id);

    public List<OrderItem> prodsale();

    public void payorder(String id);

    public void sendorder(String id);

    public void delorder(String id);


}
