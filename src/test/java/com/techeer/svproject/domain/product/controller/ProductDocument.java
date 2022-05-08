package com.techeer.svproject.domain.product.controller;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class ProductDocument {
    public static RestDocumentationResultHandler save() {

        return document("api/v1/products",
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("productId"),
                        fieldWithPath("orderId").type(JsonFieldType.STRING).description("orderId"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("city"),
                        fieldWithPath("productName").type(JsonFieldType.STRING).description("productName")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("productId"),
                        fieldWithPath("orderId").type(JsonFieldType.STRING).description("orderId"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("city"),
                        fieldWithPath("productName").type(JsonFieldType.STRING).description("productName")
                )
        );
    }

    public static RestDocumentationResultHandler getDetail() {

        return document("api/v1/products/{id}",
                pathParameters(
                        parameterWithName("id").description("조회할 product Id")
                )
        );
    }

    public static RestDocumentationResultHandler updateProduct() {

        return document("api/v1/products/{id}",
                pathParameters(
                        parameterWithName("id").description("조회할 product Id")
                ),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("productId"),
                        fieldWithPath("orderId").type(JsonFieldType.STRING).description("orderId"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("city"),
                        fieldWithPath("productName").type(JsonFieldType.STRING).description("productName")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("productId"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId"),
                        fieldWithPath("orderDate").type(JsonFieldType.NUMBER).description("orderDate")
                )
        );
    }
}
