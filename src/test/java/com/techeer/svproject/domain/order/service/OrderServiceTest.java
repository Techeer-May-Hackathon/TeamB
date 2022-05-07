package com.techeer.svproject.domain.order.service;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    private Order actualOrder;

    private User actualUser;

    private List<Order> actualOrderList;
    private UUID givenId;

    @BeforeEach
    void setup() {
        givenId = new UUID(10L, 10L);

        actualUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(null)
                .build();

        actualOrder = Order.builder()
                .user(actualUser)
                .build();

        actualOrderList = new ArrayList<>(1);
        actualOrderList.add(actualOrder);

        when(orderRepository.save(any())).thenReturn(actualOrder);
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(actualOrder));
        when(orderRepository.findAllByUserId(any())).thenReturn(actualOrderList);
        when(userRepository.findByEmail(any())).thenReturn(actualUser);

    }

    @Test
    @DisplayName("저장")
    void save() {
        // given
        Order givenOrder = Order.builder().build();
        // when
        Order expectOrder = orderService.save(givenOrder, actualUser);
        // then
        assertAll(()->assertEquals(expectOrder, actualOrder));
    }

    @Test
    @DisplayName("")
    void findById() {
        // given

        // when
        Order expectOrder = orderService.findById(givenId);
        // then
        assertAll(() -> assertEquals(expectOrder, actualOrder));
    }

    @Test
    @DisplayName("")
    void findAllByEmail() {
        // given
        String givenEmail = "user@email.com";
        // when
        List<Order> expectOrderList = orderService.findAllByEmail(givenEmail);
        // then
        assertAll(() -> assertEquals(expectOrderList, actualOrderList));
    }

    @Test
    @DisplayName("")
    void delete() {
        // given
        OrderService mockOrderService = mock(OrderService.class);
        doNothing().when(mockOrderService).delete(givenId);
        // when
        mockOrderService.delete(givenId);
        // then
        verify(mockOrderService, times(1)).delete(givenId);

    }
}