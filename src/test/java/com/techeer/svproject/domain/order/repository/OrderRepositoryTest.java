package com.techeer.svproject.domain.order.repository;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DataJpaTest
@DisplayName("오더 리포지토리 테스트")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private User actualUser;

    private Order givenOrder;

    private Order actualOrder;

    @BeforeEach
    void setup() {

        User givenUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(null)
                .build();

        actualUser = userRepository.save(givenUser);

        LocalDateTime givenLocalDateTime = LocalDateTime.now();

        givenOrder = Order.builder()
                .user(actualUser)
                .orderDate(givenLocalDateTime)
                .build();
        actualOrder = orderRepository.save(givenOrder);
    }

    @Test
    @DisplayName("오더 아이디로 검색")
    void findById() {
        // given

        // when
        Order expectOrder = orderRepository.findById(actualOrder.getId()).get();
        // then
        assertAll(
                () -> assertEquals(expectOrder, givenOrder)
        );
    }

    @Test
    @DisplayName("저장")
    void save() {
        // given

        // when
        Order expectOrder = orderRepository.save(givenOrder);
        // then
        assertAll(
                () -> assertEquals(expectOrder, givenOrder)
        );
    }

    private Order deleteSomething() {
        orderRepository.delete(actualOrder);
        return orderRepository.findById(actualOrder.getId()).get();
    }

    @Test
    @DisplayName("존재하지 않는 엔티티를 검색 할 때")
    void findDeletedOrder() {
        // given

        // when

        // then
        assertAll(
                () -> assertThrows(NoSuchElementException.class, this::deleteSomething)
        );
    }

    @Test
    @DisplayName("유저 아이디로 오더 검색")
    void findAllByUserId() {
        // given

        // when
        List<Order> expectOrderList = orderRepository.findAllByUserId(actualUser.getId());
        // then
        assertAll(() -> expectOrderList.forEach(expectOrder -> assertEquals(expectOrder, actualOrder)));
    }

    @Test
    @DisplayName("삭제")
    void delete() {
        // given
        OrderRepository testOrderRepository = mock(OrderRepository.class);
        doNothing().when(testOrderRepository).delete(givenOrder);
        // when
        testOrderRepository.delete(givenOrder);
        // then
        verify(testOrderRepository, times(1)).delete(givenOrder);

    }
}