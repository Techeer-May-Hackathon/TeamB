package com.techeer.svproject.domain.product.dto;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductResponseDtoTest {

    private Product givenProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;
    private ProductResponseDto givenProductResponseDto;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("product response dto test code")
    void ProductResponseDtoTest() {
        // given
        givenAddress = Address.builder()
                .state("경기도")
                .city("시흥")
                .street("산기대학로 237")
                .zipcode(12345)
                .build();

        givenUser = User.builder()
                .lastName("최")
                .firstName("우석")
                .email("wschoe@mail.com")
                .password("1234")
                .phoneNumber(12341234)
                .address(givenAddress)
                .build();

        givenOrder = Order.builder()
                .user(givenUser)
                .orderDate(LocalDateTime.now())
                .build();

        givenProduct = Product.builder()
                .order(givenOrder)
                .price(10000)
                .productName("테스트상품")
                .build();

        givenProductResponseDto = ProductResponseDto.builder()
                .id(givenProduct.getId())
                .price(givenProduct.getPrice())
                .productName(givenProduct.getProductName())
                .build();

        // when
        ProductResponseDto productResponseDto = new ProductResponseDto(givenProduct);
        
        // then
        assertAll(
                () -> Assertions.assertEquals(givenProductResponseDto.getId(),productResponseDto.getId()),
                () -> Assertions.assertEquals(givenProductResponseDto.getPrice(),productResponseDto.getPrice()),
                () -> Assertions.assertEquals(givenProductResponseDto.getProductName(),productResponseDto.getProductName())
        );
    }
    
}
