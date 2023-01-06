package com.norato.easymall.service;


import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.entity.Cart;

import java.util.List;

public interface CartService {
	//查找购物车是否存在该商品
	Cart findCart(Cart cart);
	//添加购物车
	void addCart(Cart cart);
	//修改购物车商品数量
	void updateCart(Cart cart);
	//显示购物车
	List<CartInfo> showcart(Integer user_id);
	
	//修改数量	
	void updateBuyNum(Cart cart);
	//删除指定商品
	void delCart(Integer cartID);
	//根据CartID查找购物车
	CartInfo findByCartID(Integer cartID);
		
}
