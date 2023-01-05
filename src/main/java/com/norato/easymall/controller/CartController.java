package com.norato.easymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.entity.Cart;
import com.norato.easymall.entity.User;
import com.norato.easymall.service.CartService;
import com.norato.easymall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "购物车管理")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Operation(summary = "添加物品到购物车")
    @RequestMapping(path = "/addCart", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject addCart(String pid, String num, String userId) {
        JSONObject json = new JSONObject();
        int buyNum = Integer.parseInt(num);
        try {
            User user = userService.selectById(Integer.parseInt(userId));
            Cart cart = new Cart(null, user.getId(), pid, buyNum);
            Cart _cart = cartService.findCart(cart);
            if (_cart == null) {
                cartService.addCart(cart);
            } else {
                cart.setCartId(_cart.getCartId());
                cartService.updateCart(cart);
            }
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("status", 200);
        return json;
    }

    @Operation(summary = "查询用户购物车")
    @GetMapping("/showcart")
    public JSONObject showcart(String userId) {
        JSONObject json = new JSONObject();
        List<CartInfo> carts;
        try {
            carts = cartService.showcart(Integer.parseInt(userId));
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("data", carts);
        json.put("status", 200);
        return json;
    }

    @Operation(summary = "更新购物车商品数量")
    @GetMapping("/updateBuyNum")
    public void updateBuyNum(Integer cartID, Integer buyNum) {
        Cart newcart = new Cart();
        newcart.setCartId(cartID);
        newcart.setNum(buyNum);
        cartService.updateBuyNum(newcart);
    }

    @Operation(summary = "删除购物车商品")
    @GetMapping("/delCart")
    public JSONObject delCart(Integer cartID) {
        JSONObject json = new JSONObject();
        try {
            cartService.delCart(cartID);
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("status", 200);
        return json;
    }
}
