package com.techeer.svproject.domain.product.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.product.dto.ProductResponseDto;
import com.techeer.svproject.domain.product.dto.ProductSaveDto;
import com.techeer.svproject.domain.product.dto.ProductUpdateDto;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.service.ProductService;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.techeer.svproject.global.utils.Constants.API_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private ProductSaveDto productSaveDto;
    private ProductUpdateDto productUpdateDto;

    private Product givenProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;

    private UUID productSaveDtoId;
    private UUID productSaveDtoOrderId;
    private UUID productId;
    private UUID orderId;

    @BeforeEach
    void setUP(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

        productSaveDto = ProductSaveDto.builder()
                .price(10000)
                .productName("TestName")
                .build();

        productUpdateDto = ProductUpdateDto.builder()
                .price(20000)
                .productName("ChangedTestName")
                .build();

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

        productSaveDtoId = new UUID(10l, 10l);
        productSaveDtoOrderId = new UUID(10l, 10l);
        productId = new UUID(10l, 10l);
        orderId = new UUID(10l, 10l);

        productSaveDto.setId(productSaveDtoId);
        productSaveDto.setOrderId(productSaveDtoOrderId);
        givenProduct.setId(productId);
        givenOrder.setId(orderId);

    }
    
    @Test
    @DisplayName("product 저장")
    void save() throws Exception {
        // given
        
        // when
        when(productService.save(any())).thenReturn(givenProduct);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productSaveDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(ProductDocument.save());
    }

    @Test
    @DisplayName("product 조회하기")
    void getDetail() throws Exception {
        // given

        // when
        when(productService.findById(any())).thenReturn(Optional.of(givenProduct));

        // then
        mockMvc.perform(get("/api/v1/products/{id}",givenProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(ProductDocument.getDetail());
    }

    @Test
    @DisplayName("product 수정하기")
    void updateProduct() throws Exception {
        // given

        // when
        when(productService.update(any(),any())).thenReturn(givenProduct);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/products/api/v1/products/{id}",givenProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

//    @Test
//    @DisplayName("주문 목록 조회하기")
//    void getByOrderId() throws Exception {
//        // given
//        ProductResponseDto productResponseDto = ProductResponseDto.builder()
//                .id(givenProduct.getId())
//                .price(givenProduct.getPrice())
//                .productName(givenProduct.getProductName())
//                .build();
//
//        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
//        productResponseDtoList.add(productResponseDto);
//
//        // when
//        when(productService.findAllByOrderId(any())).thenReturn(productResponseDtoList);
//
//        // then
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("orderId", String.valueOf(givenOrder.getId())))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(ProductDocument.getDetail());
//    }

//    @Test
//    @DisplayName("product 삭제하기")
//    void deleteProduct() throws Exception {
//        // given
////        void result = null;
//
//        // when
////        when(productService.delete(any())).thenReturn(result);
////
////        // then
////        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/products/{id}",givenProduct.getId())
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andDo(print())
////                .andDo(ProductDocument.getDetail());
//    }
}
