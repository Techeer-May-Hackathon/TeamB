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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    private Order expectOrder;

    private User expectUser;

    private List<Order> expectOrderList;
    private UUID givenId;

    @BeforeEach
    void setup() {
        givenId = new UUID(10L, 10L);

        expectUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(null)
                .build();

        expectOrder = Order.builder()
                .user(expectUser)
                .build();

        expectOrderList = new ArrayList<>(1);
        expectOrderList.add(expectOrder);

        when(orderRepository.save(any())).thenReturn(expectOrder);
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(expectOrder));
        when(orderRepository.findAllByUserId(any())).thenReturn(expectOrderList);
        when(userRepository.findByEmail(any())).thenReturn(expectUser);

    }

    @Test
    @DisplayName("저장")
    void save() {
        // given
        Order givenOrder = Order.builder().build();
        // when
        Order actualOrder = orderService.save(givenOrder, expectUser);
        // then
        assertAll(()->assertEquals(expectOrder, actualOrder));
    }

    @Test
    @DisplayName("")
    void findById() {
        // given

        // when
        Order actualOrder = orderService.findById(givenId);
        // then
        assertAll(() -> assertEquals(expectOrder, actualOrder));
    }

    @Test
    @DisplayName("")
    void findAllByEmail() {
        // given
        String givenEmail = "user@email.com";
        // when
        List<Order> actualOrderList = orderService.findAllByEmail(givenEmail);
        // then
        assertAll(() -> assertEquals(expectOrderList, actualOrderList));
    }

    void callDelete() {
        orderService.delete(givenId);
    }

    @Test
    @DisplayName("")
    void delete() {
        // given
        doNothing().when(orderRepository).delete(any());
        // when
        // then
        assertDoesNotThrow(this::callDelete);
    }
}