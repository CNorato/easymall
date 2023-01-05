package com.norato.easymall.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.SaleProduct;
import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;
import com.norato.easymall.entity.Product;
import com.norato.easymall.service.CartService;
import com.norato.easymall.service.OrderService;
import com.norato.easymall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "后台订单管理")
@RequestMapping("/admin")
public class AdminOrdersController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "根据PayState获取订单")
    @GetMapping("/shownonorder")
    public JSONObject shownonorder(String paystate) {
        List<OrderInfo> orderInfoList = findOrderInfoByPaystate(Integer.parseInt(paystate));
        JSONObject json = new JSONObject();
        json.put("data", orderInfoList);
        json.put("length", orderInfoList.size());
        return json;
    }

    private List<OrderInfo> findOrderInfoByPaystate(Integer paystate) {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        List<Order> orderList = orderService.findOrderByPaystate(paystate);

        for (Order order : orderList) {
            List<OrderItem> orderItems = orderService.orderitem(order.getId()); // 单条记录
            List<Product> products = new ArrayList<>();
            Map<Product, Integer> map = new HashMap<>();
            for (OrderItem orderItem : orderItems) {
                Product product = productService.oneProduct(orderItem.getProductId());
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

    @Operation(summary = "商品出售情况")
    @GetMapping("/showsale")
    public JSONObject showsale() {
        List<SaleProduct> saleList = findSaleProduct();
        JSONObject json = new JSONObject();
        json.put("data", saleList);
        json.put("length", saleList.size());
        return json;
    }

    private List<SaleProduct> findSaleProduct() {
        List<SaleProduct> saleList = new ArrayList<>();
        List<OrderItem> orderList = orderService.prodsale();

        for (OrderItem orderItem : orderList) {
            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setOrderItem(orderItem);
            Product product = productService.prodInfo(orderItem.getProductId());// 单条记录
            saleProduct.setProduct(product);
            saleList.add(saleProduct);
        }


        return saleList;
    }

    @Operation(summary = "配送订单")
    @GetMapping("/sendorder")
    public JSONObject sendorder(String orderId) {
        JSONObject json = new JSONObject();
        try {
            orderService.sendorder(orderId);
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("status", 200);
        return json;

    }
}
