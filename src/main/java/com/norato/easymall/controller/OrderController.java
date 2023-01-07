package com.norato.easymall.controller;

import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.result.Result;
import com.norato.easymall.entity.Order;
import com.norato.easymall.service.OrderService;
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

    @Operation(summary = "添加订单, 该方法较为奇葩, 建议重构")
    @PostMapping("/addOrder")
    public Result addOrder(HttpServletRequest request) {

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

        Result result = new Result();
        try {
            orderService.addOrder(s, order);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("添加订单成功");
    }

    @Operation(summary = "根据用户id获取订单")
    @GetMapping("/showorder")
    public Result showorder(String userId) {
        Result result = new Result();
        List<OrderInfo> orderInfoList;
        try {
            orderInfoList = orderService.findOrderInfoByUserId(Integer.parseInt(userId));
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }

        return result.success().data(orderInfoList).msg("获取订单成功");
    }



    @Operation(summary = "支付订单")
    @GetMapping("/payorder")
    public Result payorder(String orderId) {
        Result result = new Result();
        try {
            orderService.payorder(orderId);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("支付订单成功");

    }

    @Operation(summary = "删除订单")
    @GetMapping("/delorder")
    public Result deleteOrder(String orderId) {
        Result result = new Result();
        try {
            orderService.delorder(orderId);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("删除订单成功");
    }
}
