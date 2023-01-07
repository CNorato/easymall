package com.norato.easymall.controller.admin;

import com.norato.easymall.dto.ProductInfo;
import com.norato.easymall.dto.result.Result;
import com.norato.easymall.entity.Product;
import com.norato.easymall.service.ProductService;
import com.norato.easymall.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "后台商品管理")
@RequestMapping("/admin")
public class ProductsControllerAdmin {
    @Autowired
    private ProductService productService;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Operation(summary = "添加商品")
    @PostMapping(value = "/save")
    public Result save(ProductInfo myproduct, HttpServletRequest request) {
        Result result = new Result();
        try {
            String msg = productService.save(myproduct, request);
            System.out.println(msg);
            if (msg.equals("商品添加成功")) {
                result.success();
            } else {
                result.fail();
            }
            result.msg(msg);
        } catch (Exception e) {
            result.fail();
        }
        return result;
    }

    @Operation(summary = "上架商品")
    @GetMapping("/OnSale")
    public Result OnSale(String ids) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        map.put("id", ids);
        map.put("status", 1);
        try {
            productService.updateSaleStatus(map);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("设置上架成功");
    }

    @Operation(summary = "下架商品")
    @GetMapping("/OffSale")
    public Result OffSale(String ids) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        map.put("id", ids);
        map.put("status", 0);
        try {
            productService.updateSaleStatus(map);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("设置下架成功");
    }

    @Operation(summary = "上传图片")
    @PostMapping("/uploadImg")
    public Result uploadImg(MultipartFile pic) {
        Result result = new Result();
        try {
            FileUploadUtil.uploadFile(uploadPath, pic);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("上传成功");
    }

    @Operation(summary = "后台修改商品")
    @PostMapping("/update")
    public Result productUpdate(Product product) {
        Result result = new Result();
        try {
            productService.updateProduct(product);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("修改成功");
    }

    @Operation(summary = "后台删除商品")
    @GetMapping("/delete")
    public Result productDelete(String ids) {
        Result result = new Result();
        try {
            productService.deleteProduct(ids);
        } catch (Exception e) {
            return result.fail().msg(e.getMessage());
        }
        return result.success().msg("删除成功");
    }


}
