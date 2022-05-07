package com.techeer.svproject.repository;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.product.dto.ProductSaveDto;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product givenProduct;

    private Order givenOrder;

    private User givenUser;


    @BeforeEach
    void setUp(){
        givenUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(null)
                .build();

//        givenOrder = Order.builder()
//                .

//        givenProduct = Product.builder()
//                .order(null);
    }

    void findByOrderId () {
//        productRepository.findByOrderId(1);
    }




}
