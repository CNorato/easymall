package com.norato.easymall.controller;

import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.result.Result;
import com.norato.easymall.entity.Order;
import com.norato.easymall.service.CartService;
import com.norato.easymall.service.OrderService;
import com.norato.easymall.service.UserService;
import com.norato.easymall.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Operation(summary = "添加订单")
    @PostMapping("/addOrder")
    public Result addOrder(String local, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = JwtTokenUtil.getUsername(token);
        Integer userId = userService.login(username).getId();

        List<CartInfo> cartInfos = cartService.showcart(userId);
        double total = 0.0;

        for (CartInfo cart : cartInfos) {
            total += cart.getNum() * cart.getPrice();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Timestamp timeStamp = Timestamp.valueOf(time);

        Order order = new Order();
        order.setUserId(userId);
        order.setMoney(total);
        order.setReceiverinfo(local);
        order.setOrdertime(timeStamp);
        order.setId(UUID.randomUUID().toString());
        order.setPaystate(0);

        Result result = new Result();
        try {
            orderService.addOrder(cartInfos, order);
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
