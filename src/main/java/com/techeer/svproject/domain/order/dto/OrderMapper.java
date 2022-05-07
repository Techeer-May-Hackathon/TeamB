package com.techeer.svproject.domain.order.dto;

import com.techeer.svproject.domain.order.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order toEntity(OrderCreateDto dto) {
        return Order.builder()
                .orderDate(dto.getOrderDate())
                .build();
    }

    public OrderResponseDto toResponseDto(Order entity) {
        return OrderResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .orderDate(entity.getOrderDate())
                .build();
    }
}
