package com.norato.easymall.dto;

import com.norato.easymall.entity.OrderItem;
import com.norato.easymall.entity.Product;
import lombok.Data;

@Data
public class SaleProduct {

    private Product product;

    private OrderItem orderItem;
}
