package com.norato.easymall.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductInfo {

    private String name;

    private Double price;

    private String category;

    private Integer pnum;

    private MultipartFile imgurl;

    private String description;

}
