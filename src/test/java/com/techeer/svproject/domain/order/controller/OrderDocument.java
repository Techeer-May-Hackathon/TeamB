package com.techeer.svproject.domain.order.controller;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class OrderDocument {

    public static RestDocumentationResultHandler createOrder() {
        return MockMvcRestDocumentation.document("api/v1/orders",
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                )
        );
    }

    public static RestDocumentationResultHandler findAllOrderByEmil() {
        return MockMvcRestDocumentation.document("api/v1/orders",
                requestParameters(
                        parameterWithName("userEmail").description("The page to retrieve")
                ),
                responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("[].userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("[].orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                )
        );
    }

    public static RestDocumentationResultHandler getOneOrder() {
        return MockMvcRestDocumentation.document("api/v1/orders",
                pathParameters(
                        parameterWithName("id").description("The page to retrieve")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                )
        );
    }

    public static RestDocumentationResultHandler deleteOrder() {
        return MockMvcRestDocumentation.document("api/v1/orders",
                pathParameters(
                        parameterWithName("id").description("The page to retrieve")
                )
        );
    }

}
