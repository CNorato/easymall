package com.norato.easymall.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.norato.easymall.dto.ProductInfo;
import com.norato.easymall.entity.Product;
import com.norato.easymall.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "后台商品管理")
@RequestMapping("/admin")
public class ProductsControllerAdmin {
    @Autowired
    private ProductService productService;

    @Operation(summary = "添加商品")
    @PostMapping(value = "/save")
    public JSONObject save(ProductInfo myproduct, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            String msg = productService.save(myproduct, request);
            System.out.println(msg);
            if (msg.equals("商品添加成功")) {
                json.put("status", 200);
            } else {
                json.put("status", 500);
            }
            json.put("msg", msg);
        } catch (Exception e) {
            json.put("status", 500);
        }
        return json;
    }

    @Operation(summary = "上架商品")
    @GetMapping("/OnSale")
    public JSONObject OnSale(String ids) {
        JSONObject json = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        map.put("id", ids);
        map.put("status", 1);
        try {
            productService.updateSaleStatus(map);
            json.put("status", 200);
        } catch (Exception e) {
            json.put("status", 500);
            json.put("msg", "设置上架失败，ProductControllerAdmin找错去吧");
        }
        return json;
    }

    @Operation(summary = "下架商品")
    @GetMapping("/OffSale")
    public JSONObject OffSale(String ids) {
        JSONObject json = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        map.put("id", ids);
        map.put("status", 0);
        try {
            productService.updateSaleStatus(map);
            json.put("status", 200);
        } catch (Exception e) {
            json.put("status", 500);
            json.put("msg", "设置下架失败，ProductControllerAdmin找错去吧");
        }
        System.out.println(json);
        return json;
    }

    @Operation(summary = "上传图片")
    @PostMapping("/uploadImg")
    public JSONObject uploadImg(HttpServletRequest request) {

        JSONObject json = new JSONObject();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("pic");
            json.put("filename", multipartFile.getOriginalFilename());
        } catch (Exception e) {
            json.put("error", 0);
        }
        return json;
    }

    @Operation(summary = "后台修改商品")
    @PostMapping("/update")
    public JSONObject productUpdate(Product product) {
        JSONObject json = new JSONObject();
        try {
            System.out.println("更新正在执行");
            productService.updateProduct(product);
            json.put("status", 200);
        } catch (Exception e) {
            json.put("status", 500);
            json.put("msg", "出错啦，还不快去productController查bug");
        }
        return json;
    }

    @Operation(summary = "后台删除商品")
    @GetMapping("/delete")
    public JSONObject productDelete(String ids) {
        JSONObject json = new JSONObject();
        try {
            System.out.println("正在删除商品");
            productService.deleteProduct(ids);
            json.put("status", 200);
        } catch (Exception e) {
            json.put("msg", "删除出问题了");
            json.put("status", 500);
        }
        return json;
    }


}
