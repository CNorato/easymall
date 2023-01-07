package com.norato.easymall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.norato.easymall.dto.CartInfo;
import com.norato.easymall.dto.OrderInfo;
import com.norato.easymall.dto.SaleProduct;
import com.norato.easymall.entity.Order;
import com.norato.easymall.entity.OrderItem;
import com.norato.easymall.entity.Product;
import com.norato.easymall.mapper.OrderItemMapper;
import com.norato.easymall.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	@Override
	@Transactional
	public void addOrder(String cartIds, Order myOrder) {
		String[] arrCartIds=cartIds.split(",");
		double sum=0.0;
		for(String cartID:arrCartIds){
			Integer cid = Integer.parseInt(cartID);
			CartInfo cartInfo = cartService.findByCartID(cid);
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
			cartService.delCart(cid);
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
			Map<String,Object> map= new HashMap<>();
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
        Product product = productService.prodInfo(pid);
        int buynum = (int) map.get("buynum");
        product.setSoldnum(product.getSoldnum()+buynum);
        product.setPnum(product.getPnum()-buynum);

        productService.updateProduct(product);
    }

	public List<OrderInfo> findOrderInfoByUserId(Integer userId) {
		List<Order> orderList = findOrderByUserId(userId);

		return getOrderInfoList(orderList);
	}

	private List<OrderInfo> getOrderInfoList(List<Order> orderList) {
		List<OrderInfo> orderInfoList = new ArrayList<>();
		for (Order order : orderList) {
			List<OrderItem> orderItems = orderitem(order.getId()); // 单条记录
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

	public List<SaleProduct> findSaleProduct() {
		List<SaleProduct> saleList = new ArrayList<>();
		List<OrderItem> orderList = prodsale();

		for (OrderItem orderItem : orderList) {
			SaleProduct saleProduct = new SaleProduct();
			saleProduct.setOrderItem(orderItem);
			Product product = productService.prodInfo(orderItem.getProductId());// 单条记录
			saleProduct.setProduct(product);
			saleList.add(saleProduct);
		}

		return saleList;
	}

	public List<OrderInfo> findOrderInfoByPaystate(Integer paystate) {
		List<Order> orderList = findOrderByPaystate(paystate);

		return getOrderInfoList(orderList);
	}
}
