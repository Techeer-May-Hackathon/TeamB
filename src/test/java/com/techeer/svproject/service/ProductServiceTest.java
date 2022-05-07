package com.techeer.svproject.service;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.product.dto.ProductSaveDto;
import com.techeer.svproject.domain.product.dto.ProductUpdateDto;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import com.techeer.svproject.domain.product.service.ProductService;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    private Product givenProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;

    private UUID givenId;
    private UUID givenOrderId;

    @BeforeEach
    void setUp(){
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

        when(orderRepository.findById(any())).thenReturn(Optional.of(givenOrder));
        when(productRepository.findById(any())).thenReturn(Optional.of(givenProduct));
        when(productRepository.save(any())).thenReturn(givenProduct);

    }

    @Test
    @DisplayName("product service save")
    void save() {
        // given
        ProductSaveDto productSaveDto = ProductSaveDto.builder()
                .id(givenId)
                .orderId(givenOrderId)
                .price(10000)
                .productName("테스트상품")
                .build();

        // when
        Product product = productService.save(productSaveDto);

        // then
        assertAll(
                () -> Assertions.assertEquals(givenProduct.getOrder(), product.getOrder()),
                () -> Assertions.assertEquals(givenProduct.getProductName(), product.getProductName()),
                () -> Assertions.assertEquals(givenProduct.getPrice(), product.getPrice())
        );
    }

    @Test
    @DisplayName("product service update")
    void update() {
        // given
        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder()
                .id(givenId)
                .orderId(givenOrderId)
                .price(20000)
                .productName("변경된이름")
                .build();

        // when
        Product product = productService.update(givenId, productUpdateDto);

        // then
        assertAll(
                () -> Assertions.assertEquals("변경된이름",product.getProductName()),
                () -> Assertions.assertEquals(20000,product.getPrice())
        );
    }
}
