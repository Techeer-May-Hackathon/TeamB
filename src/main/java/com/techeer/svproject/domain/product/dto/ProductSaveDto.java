package com.techeer.svproject.domain.product.dto;

import com.techeer.svproject.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDto {
    private UUID id;
    private UUID orderId;
    private int price;
    private String productName;

    public Product toEntity() {
        return Product.builder()
                .price(price)
                .productName(productName)
                .build();
    }

    public static ProductSaveDto fromEntity(Product product) {
        return ProductSaveDto.builder()
                .id(product.getId())
                .orderId(product.getOrder().getId())
                .price(product.getPrice())
                .productName(product.getProductName())
                .build();
    }
}
