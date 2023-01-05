package com.norato.easymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    @Select("select cartID,pid,num,name,price,imgurl from cart,products " +
            "where user_id=#{user_id} and cart.pid=products.id")
    List<CartInfo> showcart(Integer user_id);

    @Select("select cartID,pid,num,name,price,imgurl from cart,products " +
            "where cartID=#{cartID} and cart.pid=products.id")
    CartInfo findByCartID(Integer cartID);
}
