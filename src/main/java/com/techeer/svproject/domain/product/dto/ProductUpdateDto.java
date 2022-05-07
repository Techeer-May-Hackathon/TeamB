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
public class ProductUpdateDto {
    private UUID id;
    private UUID orderId;
    private int price;
    private String productName;

    public static ProductUpdateDto fromEntity(Product product) {
        return ProductUpdateDto.builder()
                .id(product.getId())
                .orderId(product.getOrder().getId())
                .price(product.getPrice())
                .productName(product.getProductName())
                .build();
    }
}
