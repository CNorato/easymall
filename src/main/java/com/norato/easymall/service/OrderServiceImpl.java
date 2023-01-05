package com.norato.easymall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;
import com.norato.easymall.entity.Product;
import com.norato.easymall.mapper.CartMapper;
import com.norato.easymall.mapper.OrderItemMapper;
import com.norato.easymall.mapper.OrderMapper;
import com.norato.easymall.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;

	@Autowired
	private ProductsMapper productsMapper;

	@Override
	@Transactional
	public void addOrder(String cartIds, Order myOrder) {
		String[] arrCartIds=cartIds.split(",");
		double sum=0.0;
		for(String cartID:arrCartIds){
			Integer cid = Integer.parseInt(cartID);
			CartInfo cartInfo = cartMapper.findByCartID(cid);
			String pid= cartInfo.getPid();
			int buynum= cartInfo.getNum();
			Double price = cartInfo.getPrice();
			sum+=buynum * price;
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(myOrder.getId());
			orderItem.setProductId(pid);
			orderItem.setBuynum(buynum);

			orderItemMapper.insert(orderItem);   //变化

			Map<String,Object> map= new HashMap<>();
			map.put("pid", pid);
			map.put("buynum", buynum);
			updateSoldNum(map);              ///修改
			cartMapper.deleteById(cid);
		}
		myOrder.setMoney(sum);
		orderMapper.insert(myOrder);
		
	}

	@Override
	public List<Order> findOrderByUserId(Integer user_id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user_id);
        return orderMapper.selectList(wrapper);
	}
	@Override
	public List<Order> findOrderByPaystate(Integer paystate) {
		QueryWrapper<Order> wrapper = new QueryWrapper<>();
		wrapper.eq("paystate", paystate);
		return orderMapper.selectList(wrapper);
	}
	@Override
	public List<OrderItem> orderitem(String order_id) {
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",order_id);
        return orderItemMapper.selectList(wrapper);
	}
	@Override
	public List<OrderItem> prodsale() {
		QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
		wrapper.select("sum(buynum) as buynum,product_id");
		wrapper.groupBy("product_id");
		wrapper.orderByDesc("buynum");
		return orderItemMapper.selectList(wrapper);
	}
	@Override
	public void sendorder(String id) {

		Order order = orderMapper.selectById(id);
		order.setPaystate(2);
		orderMapper.updateById(order);
	}
	@Override
	public void payorder(String id) {
//        update orders set paystate=1 where id=#{id}
        Order order = orderMapper.selectById(id);
        order.setPaystate(1);
        orderMapper.updateById(order);
	}

	@Override
	@Transactional   // 事 务
	public void delorder(String id) {
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        List<OrderItem> orderItems = orderItemMapper.selectList(wrapper);

		for(OrderItem orderItem:orderItems){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("pid", orderItem.getProductId());
			map.put("buynum", -orderItem.getBuynum());
			updateSoldNum(map);	              ////// 修改
		}
		orderMapper.deleteById(id);
		orderItemMapper.delete(wrapper);
	}

    //    updateSoldNum
//    update products set soldNum=soldNum+#{buynum},pnum=pnum-#{buynum}
//		 where id=#{pid}
    public void updateSoldNum(Map<String,Object> map) {
        String pid = (String) map.get("pid");
        Product product = productsMapper.selectById(pid);
        int buynum = (int) map.get("buynum");
        product.setSoldnum(product.getSoldnum()+buynum);
        product.setPnum(product.getPnum()-buynum);

        productsMapper.updateById(product);
    }


}
