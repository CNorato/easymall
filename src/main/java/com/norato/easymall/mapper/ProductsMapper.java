package com.norato.easymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norato.easymall.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductsMapper extends BaseMapper<Product> {
}
