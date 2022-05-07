package com.techeer.svproject.domain.order.repository;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("오더 리포지토리 테스트")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    private Order givenOrder;

    private Order savedOrder;

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

        savedUser = userRepository.save(givenUser);

        LocalDateTime givenLocalDateTime = LocalDateTime.now();

        givenOrder = Order.builder()
                .user(savedUser)
                .orderDate(givenLocalDateTime)
                .build();
        savedOrder = orderRepository.save(givenOrder);
    }

    @Test
    @DisplayName("오더 아이디로 검색")
    void findById() {
        // given

        // when
        Order expectOrder = orderRepository.findById(savedOrder.getId()).get();
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
        orderRepository.delete(savedOrder);
        return orderRepository.findById(savedOrder.getId()).get();

    }

    @Test
    @DisplayName("삭제")
    void delete() {
        // given

        // when
        // then
        assertAll(
                () -> Assertions.assertThrows(NoSuchElementException.class, this::deleteSomething)
        );
    }

    @Test
    @DisplayName("유저 아이디로 오더 검색")
    void findAllByUserId() {
        // given

        // when
        List<Order> expectOrderList = orderRepository.findAllByUserId(savedUser.getId());
        // then
        assertAll(() -> expectOrderList.forEach(expect -> assertEquals(expect, savedOrder)));
    }
}