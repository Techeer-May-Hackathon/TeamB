package com.techeer.svproject.domain.order.dto;

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
public class OrderResponseDto {
            private UUID id;
            private UUID userId;
            private LocalDateTime orderDate;
}
