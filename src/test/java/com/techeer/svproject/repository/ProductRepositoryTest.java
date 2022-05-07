package com.techeer.svproject.repository;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Product givenProduct;
    private Product savedProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;
    private Order savedOrder;

    @BeforeEach
    void setUp(){
        givenAddress = Address.builder()
                .state("경기도")
                .city("시흥")
                .street("산기대학로 237")
                .zipcode(12345)
                .build();
        Address savedAddress = addressRepository.save(givenAddress);

        givenUser = User.builder()
                .lastName("최")
                .firstName("우석")
                .email("wschoe@mail.com")
                .password("1234")
                .phoneNumber(12341234)
                .address(savedAddress)
                .build();
        User savedUser = userRepository.save(givenUser);

        givenOrder = Order.builder()
                .user(savedUser)
                .orderDate(LocalDateTime.now())
                .build();
        savedOrder = orderRepository.save(givenOrder);

        givenProduct = Product.builder()
                .order(savedOrder)
                .price(10000)
                .productName("테스트상품")
                .build();
        savedProduct = productRepository.save(givenProduct);

        UUID ID = new UUID(10l, 10l);
    }

    @Test
    @DisplayName("Product repository save")
    void save() {
        // given
        
        // when

        // then
        Assertions.assertEquals(givenProduct,savedProduct);
    }
    
    @Test
    @DisplayName("product repository find by id")
    void findById() {
        // given
        
        // when
        Optional<Product> findProduct = productRepository.findById(givenProduct.getId());
        // then
        if(findProduct.isPresent()) {
            Product product = findProduct.get();
            assertAll(
                    () -> Assertions.assertEquals(givenProduct,product),
                    () -> Assertions.assertEquals(givenProduct.getId(),product.getId())
            );
        }
    }

    @Transactional
    @Test
    @DisplayName("product repository find all test")
    void findAll() {
        // given
        
        // when
        Product givenProduct2 = Product.builder()
                .order(savedOrder)
                .price(10000)
                .productName("테스트상품2")
                .build();
        Product givenProduct3 = Product.builder()
                .order(savedOrder)
                .price(10000)
                .productName("테스트상품3")
                .build();
        Product savedProduct2 = productRepository.save(givenProduct2);
        Product savedProduct3 = productRepository.save(givenProduct3);

        List<Product> result = productRepository.findAll();
        // then
        Assertions.assertEquals(3,result.size());
    }
    
    @Test
    @DisplayName("product repository find by order id")
    void findByOrderId() {
        // given
        
        // when
        Order order = givenProduct.getOrder();
        UUID id = order.getId();
        List<Product> products = productRepository.findByOrderId(id);
        
        // then
        Assertions.assertEquals(1,products.size());
    }

    @Transactional
    @Test
    @DisplayName("product repository delete test")
    void delete() {
        // given
        
        // when
        UUID id = givenProduct.getId();
        productRepository.delete(givenProduct);
        Optional<Product> findProduct = productRepository.findById(id);
        // then
        Assertions.assertEquals(false,findProduct.isPresent());
    }
}
