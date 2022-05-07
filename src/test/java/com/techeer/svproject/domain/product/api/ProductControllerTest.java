package com.techeer.svproject.domain.product.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.api.AddressDocument;
import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.product.controller.ProductController;
import com.techeer.svproject.domain.product.dto.ProductSaveDto;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.techeer.svproject.global.utils.Constants.API_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ProductControllerTest.class)
@AutoConfigureRestDocs
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private Product givenProduct;
    private Order givenOrder;
    private User givenUser;
    private Address givenAddress;

    @BeforeEach
    void setUP(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(documentationConfiguration(restDocumentationContextProvider))
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
    }
    
    @Test
    @DisplayName("post /{id}/")
    void save() throws Exception {
        // given
        ProductSaveDto productSaveDto = ProductSaveDto.fromEntity(givenProduct);
        String requestBody = objectMapper.writeValueAsString(productSaveDto);
        
        // when
        when(productService.save(any())).thenReturn(givenProduct);

        // then
        mockMvc.perform(post(API_PREFIX+"/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(AddressDocument.getAddress());
    }

    @Test
    @DisplayName("get /{id}/")
    void getDetail() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("put API_PREFIX+/products/{id}/")
    void updateProduct() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("get ")
    void getByOrderId() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("delete /{id}/")
    void deleteProduct() {
        // given

        // when

        // then
    }
}
