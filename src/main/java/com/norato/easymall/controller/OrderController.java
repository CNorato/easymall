package com.norato.easymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;
import com.norato.easymall.entity.Product;
import com.norato.easymall.service.OrderService;
import com.norato.easymall.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Tag(name = "订单管理")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productsService;

    @Operation(summary = "添加订单, 该方法较为奇葩, 建议重构")
    @PostMapping("/addOrder")
    public JSONObject addOrder(HttpServletRequest request) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Timestamp timeStamp = Timestamp.valueOf(time);

        Set<String> set = request.getParameterMap().keySet();
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (String s : set) {
            if (s.startsWith("orderItems[" + index + "].cartID")) {
                builder.append(request.getParameter(s));
                builder.append(",");
                index++;
            }
        }
        String s = builder.toString();
        s = s.substring(0, s.length() - 1);
        Order order = new Order();
        order.setMoney(Double.valueOf(request.getParameter("orderMoney")));
        order.setUser_id(Integer.valueOf(request.getParameter("userId")));
        order.setReceiverinfo(request.getParameter("orderReceiverinfo"));
        order.setOrdertime(timeStamp);
        order.setId(UUID.randomUUID().toString());
        order.setPaystate(0);

        JSONObject json = new JSONObject();
        try {
            orderService.addOrder(s, order);
        } catch (Exception e) {
            json.put("status", 200);
            return json;
        }
        json.put("status", 200);
        return json;
    }

    @Operation(summary = "根据用户id获取订单")
    @GetMapping("/showorder")
    public JSONObject showorder(String userId) {
        List<OrderInfo> orderInfoList = findOrderInfoByUserId(Integer.parseInt(userId));
        JSONObject json = new JSONObject();
        json.put("data", orderInfoList);
        json.put("length", orderInfoList.size());
        return json;
    }

    private List<OrderInfo> findOrderInfoByUserId(Integer userId) {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        List<Order> orderList = orderService.findOrderByUserId(userId);

        for (Order order : orderList) {
            List<OrderItem> orderItems = orderService.orderitem(order.getId()); // 单条记录
            List<Product> products = new ArrayList<>();
            Map<Product, Integer> map = new HashMap<>();
            for (OrderItem orderItem : orderItems) {
                Product product = productsService.oneProduct(orderItem.getProductId());
                products.add(product);
                map.put(product, orderItem.getBuynum());  //哪个商品 ，买了多少
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrder(order);
            orderInfo.setMap(map);
            orderInfo.setProductsList(products);
            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }

    @Operation(summary = "支付订单")
    @GetMapping("/payorder")
    public JSONObject payorder(String orderId) {
        JSONObject json = new JSONObject();
        try {
            orderService.payorder(orderId);
            ;
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("status", 200);
        return json;

    }

    @Operation(summary = "删除订单")
    @GetMapping("/delorder")
    public JSONObject deleteOrder(String orderId) {
        JSONObject json = new JSONObject();
        try {
            orderService.delorder(orderId);
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        orderService.delorder(orderId);
        json.put("status", 200);
        return json;
    }
}
