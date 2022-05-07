package com.techeer.svproject.domain.order.dto;

import com.techeer.svproject.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {

    private UUID id;

    private UUID userId;

    private LocalDateTime orderDate;
}
