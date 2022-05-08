package com.techeer.svproject.domain.order.dto;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {

    private final OrderMapper orderMapper = new OrderMapper();

    private Order expectOrder;

    private final LocalDateTime givenLocalDateTime = LocalDateTime.now();

    private OrderResponseDto expectOrderResponseDto;

    @BeforeEach
    void setup() {
        UUID givenId = new UUID(10L, 10L);

        User givenUser = new User(givenId, "김", "영준", "Over@naver.com", "qwer12345!", 1023132312, null, null);

        expectOrder = new Order(givenId, givenUser, null, givenLocalDateTime);

        expectOrderResponseDto = OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build();

    }

    @Test
    @DisplayName("")
    void toEntity() {
        // given
        OrderCreateDto givenOrderCreateDto = OrderCreateDto.builder()
                .orderDate(givenLocalDateTime)
                .build();
        // when
        Order actualOrder = orderMapper.toEntity(givenOrderCreateDto);
        // then
        assertAll(
                () -> assertEquals(actualOrder.getOrderDate(), expectOrder.getOrderDate())
        );
    }

    @Test
    @DisplayName("")
    void toResponseDto() {
        // given

        // when
        OrderResponseDto actualOrderResponseDto = orderMapper.toResponseDto(expectOrder);
        // then
        assertAll(
                () -> assertEquals(actualOrderResponseDto.getId(), expectOrderResponseDto.getId()),
                () -> assertEquals(actualOrderResponseDto.getUserId(), expectOrderResponseDto.getUserId()),
                () -> assertEquals(actualOrderResponseDto.getOrderDate(), expectOrderResponseDto.getOrderDate())
        );
    }
}