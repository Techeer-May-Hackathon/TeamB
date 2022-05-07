package com.techeer.svproject.domain.order.controller;

import com.techeer.svproject.domain.order.dto.OrderCreateDto;
import com.techeer.svproject.domain.order.dto.OrderMapper;
import com.techeer.svproject.domain.order.dto.OrderResponseDto;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.service.OrderService;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Order 컨틑롤러 테스트")
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    private UUID givenId;

    private final LocalDateTime givenLocalDateTime = LocalDateTime.now();

    private Order expectOrder;

    private OrderResponseDto expectOrderResponseDto;

    @BeforeEach
    void setup() {
        givenId = new UUID(10L, 10L);

        expectOrder = Order.builder()
                .user(User.builder().build())
                .orderDate(givenLocalDateTime)
                .build();

        expectOrderResponseDto = OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build();

        when(orderService.save(any(), any())).thenReturn(expectOrder);
        when(orderService.findById(any())).thenReturn(expectOrder);

        when(orderMapper.toEntity(any())).thenReturn(expectOrder);
        when(orderMapper.toResponseDto(any())).thenReturn(expectOrderResponseDto);

    }

    @Test
    @DisplayName("컨트롤러 save")
    void save() {
        //given
        when(userService.findById(any())).thenReturn(User.builder().build());
        OrderCreateDto givenOrderCreateDto = OrderCreateDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(LocalDateTime.now())
                .build();
        ResponseEntity expectResponseEntity = new ResponseEntity<>(expectOrderResponseDto, HttpStatus.CREATED);
        //when
        ResponseEntity actualResponseEntity = orderController.save(givenOrderCreateDto);
        //then
        assertEquals(expectResponseEntity, actualResponseEntity);
    }

    @Test
    @DisplayName("컨트롤러 ID로 조회")
    void getDetail() {
        // given
        ResponseEntity actualResponseEntity = ResponseEntity.ok().body(expectOrderResponseDto);
        // when
        ResponseEntity expectResponseEntity = orderController.getDetail(givenId);
        // then
        assertEquals(expectResponseEntity, actualResponseEntity);
    }

    @Test
    void getByUserId() {
        //given
        List<Order> actualOrderList = new ArrayList<>(1);
        actualOrderList.add(expectOrder);
        when(orderService.findAllByEmail(any())).thenReturn(actualOrderList);

        List<OrderResponseDto> actualOrderResponseList = new ArrayList<>(1);
        actualOrderResponseList.add(OrderResponseDto.builder()
                .id(givenId)
                .userId(givenId)
                .orderDate(givenLocalDateTime)
                .build());

        String givenEmail = "user@email.com";

        ResponseEntity actualResponseEntity = new ResponseEntity<>(actualOrderResponseList, HttpStatus.ACCEPTED);
        //when
        ResponseEntity expectResponseEntity = orderController.getByUserId(givenEmail);
        //then
        assertAll(
                () -> assertEquals(expectResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode()),
                () -> assertEquals(expectResponseEntity.getBody().getClass(), actualResponseEntity.getBody().getClass())
                // 객체 비교 수준의 테스트 코드, 좋지 않음
        );
    }

    @Test
    @DisplayName("삭제")
    void deleteOrder() {
        //given
        doNothing().when(orderService).delete(any());
        ResponseEntity actualResponseEntity = ResponseEntity.ok().build();
        //when
        ResponseEntity<OrderResponseDto> expectResponseEntity = orderController.deleteOrder(givenId);
        //then
        assertEquals(expectResponseEntity, actualResponseEntity);
    }
}