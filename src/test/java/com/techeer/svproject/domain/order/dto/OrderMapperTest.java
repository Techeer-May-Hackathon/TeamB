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

    private Order actualOrder;

    private final LocalDateTime givenLocalDateTime = LocalDateTime.now();

    private OrderResponseDto actualOrderResponseDto;

    @BeforeEach
    void setup() {
        UUID givenId = new UUID(10L, 10L);

        User givenUser = new User(givenId, "김", "영준", "Over@naver.com", "qwer12345!", 1023132312, null, null);

        actualOrder = new Order(givenId, givenUser, null, givenLocalDateTime);

        actualOrderResponseDto = OrderResponseDto.builder()
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
        Order expectOrder = orderMapper.toEntity(givenOrderCreateDto);
        // then
        assertAll(
                () -> assertEquals(expectOrder.getOrderDate(), actualOrder.getOrderDate())
        );
    }

    @Test
    @DisplayName("")
    void toResponseDto() {
        // given

        // when
        OrderResponseDto expectOrderResponseDto = orderMapper.toResponseDto(actualOrder);
        // then
        assertAll(
                () -> assertEquals(expectOrderResponseDto.getId(), actualOrderResponseDto.getId()),
                () -> assertEquals(expectOrderResponseDto.getUserId(), actualOrderResponseDto.getUserId()),
                () -> assertEquals(expectOrderResponseDto.getOrderDate(), actualOrderResponseDto.getOrderDate())
        );
    }
}