package com.norato.easymall.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norato.easymall.dto.ProductInfo;
import com.norato.easymall.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface ProductService {
    //查找商品类别
    List<String> allcategories();

    //查找商品
    List<Product> prodlist(Map<String, Object> map);

    Page<Product> selectPage(Page<Product> page, QueryWrapper<Product> wrapper);

    //查找商品详情
    Product prodInfo(String pid);

    //分类查找商品
    List<Product> prodclass(String proclass);

    Product oneProduct(String product_id);

    String save(ProductInfo myProduct, HttpServletRequest request);

    List<Product> allprods();

    void updateSaleStatus(Map<String, Object> map);

    //更新商品
    void updateProduct(Product Product);

    //删除商品
    void deleteProduct(String id);

    //查询商品
    List<Product> findProductById(Integer Id);
}
