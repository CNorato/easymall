package com.norato.easymall.service;

import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.entity.Cart;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.norato.easymall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
	private CartMapper cartMapper;

	@Override
	public Cart findCart(Cart cart) {
//        select * from cart where user_id=#{user_id} and pid=#{pid}
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", cart.getUser_id());
        wrapper.eq("pid", cart.getPid());
        return cartMapper.selectOne(wrapper);
	}

	@Override
	public void addCart(Cart cart) {
//        insert into cart(user_id,pid,num) values(#{user_id},#{pid},#{num})
        cartMapper.insert(cart);
	}

	@Override
	public void updateCart(Cart cart) {
//        update cart set num=num+#{num} where cartID=#{cartID}
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        Cart cart1 = cartMapper.selectById(cart.getCartId());
        cart1.setNum(cart1.getNum() + cart.getNum());
        cartMapper.updateById(cart1);
	}

	@Override
	public List<CartInfo> showcart(Integer user_id) {
//        select cartID,pid,num,name,price,imgurl from cart,products
//		where user_id=#{user_id} and cart.pid=products.id
		return cartMapper.showcart(user_id);
	}

	@Override
	public void updateBuyNum(Cart cart) {
//        update cart set num=#{num} where cartID=#{cartID}
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        Cart cart1 = cartMapper.selectById(cart.getCartId());
        cart1.setNum(cart.getNum());
        cartMapper.updateById(cart1);
		
	}
	@Override
	public void delCart(Integer cartID) {
//        delete from cart where cartID=#{cartID}
        cartMapper.deleteById(cartID);
	}

	@Override
	public CartInfo findByCartID(Integer cartID) {
//        select cartID,pid,num,name,price,imgurl from cart,products
//		where cartID=#{cartID} and cart.pid=products.id
		return cartMapper.findByCartID(cartID);
	}
	
}
