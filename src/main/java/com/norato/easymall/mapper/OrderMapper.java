package com.norato.easymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norato.easymall.entity.Order;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
