package com.norato.easymall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norato.easymall.dto.result.Result;
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

    @Operation(summary = "获取商品列表(By Category)")
    @GetMapping("/pageManage")
    public Result pageManage(String page, String rows, @RequestParam(required = false) String proclass){
        Result result = new Result();
        Page<Product> page1 = new Page<>(Long.parseLong(page), Long.parseLong(rows));
        try {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            if (proclass != null) {
                wrapper.eq("category", proclass);
            }
            productService.selectPage(page1, wrapper);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("查询成功").data(page1.getRecords());
    }

    @Operation(summary = "获取商品列表(By Name)")
    @GetMapping("/query")
    public Result search(String query, String page, String rows){
        Result result = new Result();
        Page<Product> page1 = new Page<>(Long.parseLong(page), Long.parseLong(rows));
        try {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.like("name",query);
            productService.selectPage(page1, wrapper);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("查询成功").data(page1.getRecords());
    }

    @Operation(summary = "商品详情")
	@GetMapping("/prodInfo")
	public Result prodInfo(String pid){
        Result result = new Result();
        Product product;
        try {
            product = productService.prodInfo(pid);
        } catch (Exception e) {
            return result.fail().msg("获取商品详情失败");
        }
        return result.success().data(product);
	}
}
