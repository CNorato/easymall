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
    public List<String> allcategories();

    //查找商品
    public List<Product> prodlist(Map<String, Object> map);

    public Page<Product> selectPage(Page<Product> page, QueryWrapper<Product> wrapper);

    //查找商品详情
    public Product prodInfo(String pid);

    //分类查找商品
    public List<Product> prodclass(String proclass);

    public Product oneProduct(String product_id);

    public String save(ProductInfo myProduct, HttpServletRequest request);

    public List<Product> allprods();

    public void updateSaleStatus(Map<String, Object> map);

    //更新商品
    public void updateProduct(Product Product);

    //删除商品
    public void deleteProduct(String id);

    //查询商品
    public List<Product> findProductById(Integer Id);
}
