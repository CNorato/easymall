package com.norato.easymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norato.easymall.entity.Product;
import com.norato.easymall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "商品管理")
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

    @Operation(summary = "获取商品列表")
    @GetMapping("/pageManage")
    public JSONObject pageManage(String page, String rows, @RequestParam(required = false) String proclass){
        JSONObject json = new JSONObject();
        Page<Product> page1 = new Page<>(Long.parseLong(page), Long.parseLong(rows));
        Page<Product> products;
        try {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            if (proclass != null) {
                wrapper.eq("category", proclass);
            }
            products = productService.selectPage(page1, wrapper);
        } catch (Exception e) {
            //json.put("status", 500);
            return json;
        }
        //json.put("status", 200);
        json.put("rows", page1.getRecords());
        return json;
    }

    @Operation(summary = "获取商品列表")
    @GetMapping("/query")
    public JSONObject search(String query, String page, String rows){
        JSONObject json = new JSONObject();
        Page<Product> page1 = new Page<>(Long.parseLong(page), Long.parseLong(rows));
        Page<Product> products;
        try {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.like("name",query);
            products = productService.selectPage(page1, wrapper);
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("status", 200);
        json.put("data", page1.getRecords());
        return json;
    }

    @Operation(summary = "商品详情")
	@GetMapping("/prodInfo")
	public JSONObject prodInfo(String pid){
        System.out.println("请求\n\n\n\n\n");
        JSONObject json = new JSONObject();
        Product product;
        try {
            product = productService.prodInfo(pid);
        } catch (Exception e) {
            json.put("status", 500);
            return json;
        }
        json.put("data", product);
        json.put("status", 200);
        return json;
	}
}
