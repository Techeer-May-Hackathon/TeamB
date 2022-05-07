package com.techeer.svproject.domain.product.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.domain.product.controller.ProductController;
import com.techeer.svproject.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ProductControllerTest.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUP(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }
    
    @Test
    @DisplayName("post /{id}/")
    void save() {
        // given
        
        // when
        
        // then
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
