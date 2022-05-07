package com.techeer.svproject.service;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import com.techeer.svproject.domain.product.service.ProductService;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Product givenProduct;

    private Product givenProduct;
    private Product savedProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;
    private Order savedOrder;

    @BeforeEach
    void setUp(){
        when(productRepository.findById(any())).thenReturn();
        when(productRepository.save(any())).thenReturn();
        when(productRepository.findAll()).thenReturn();

    }

    @Test
    @DisplayName("product service save")
    void save() {
        // given

        // when

        // then
    }

}
