package com.norato.easymall.controller.admin;

import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.SaleProduct;
import com.norato.easymall.dto.result.Result;
import com.norato.easymall.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "后台订单管理")
@RequestMapping("/admin")
public class AdminOrdersController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "根据PayState获取订单")
    @GetMapping("/shownonorder")
    public Result shownonorder(String paystate) {
        Result result = new Result();
        List<OrderInfo> orderInfoList;
        try {
            orderInfoList = orderService.findOrderInfoByPaystate(Integer.parseInt(paystate));
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("查询成功").data(orderInfoList);
    }

    @Operation(summary = "商品出售情况")
    @GetMapping("/showsale")
    public Result showsale() {
        Result result = new Result();
        List<SaleProduct> saleList;
        try {
            saleList = orderService.findSaleProduct();
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().data(saleList);
    }

    @Operation(summary = "配送订单")
    @GetMapping("/sendorder")
    public Result sendorder(String orderId) {
        Result result = new Result();
        try {
            orderService.sendorder(orderId);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("配送成功");

    }
}
