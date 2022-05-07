package com.techeer.svproject.domain.order.entity;

import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order givenOrder;

    private User givenUser;

    private LocalDateTime givenDateTime;

    @BeforeEach()
    void setup() {
        givenUser = User.builder().build();
        givenDateTime = LocalDateTime.now();

        givenOrder = new Order(givenUser, givenDateTime);

    }

    @Test
    @DisplayName("오더 엔티티 생성자")
    void builder() {
        //given

        //when
        Order testOrder = Order.builder()
                .user(givenUser)
                .orderDate(givenDateTime)
                .build();

        //then
        Assertions.assertAll(
                () -> assertEquals(testOrder.getUser(), givenOrder.getUser()),
                () -> assertEquals(testOrder.getOrderDate(), givenOrder.getOrderDate())
        );

    }

}