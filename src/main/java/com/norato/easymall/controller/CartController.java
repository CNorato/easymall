package com.norato.easymall.controller;

import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.dto.result.Result;
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
    public Result addCart(String pid, String num, String userId) {
        Result result = new Result();
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
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("添加成功");
    }

    @Operation(summary = "查询用户购物车")
    @GetMapping("/showcart")
    public Result showcart(String userId) {
        Result result = new Result();
        List<CartInfo> carts;
        try {
            carts = cartService.showcart(Integer.parseInt(userId));
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("查询成功").data(carts);
    }

    @Operation(summary = "更新购物车商品数量")
    @GetMapping("/updateBuyNum")
    public Result updateBuyNum(Integer cartID, Integer buyNum) {
        Result result = new Result();
        Cart newcart = new Cart();
        newcart.setCartId(cartID);
        newcart.setNum(buyNum);
        try {
            cartService.updateBuyNum(newcart);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("更新成功");
    }

    @Operation(summary = "删除购物车商品")
    @GetMapping("/delCart")
    public Result delCart(Integer cartID) {
        Result result = new Result();
        try {
            cartService.delCart(cartID);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("删除成功");
    }
}
