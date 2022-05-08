package com.techeer.svproject.domain.order.controller;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class OrderDocument {

    public static RestDocumentationResultHandler postOrder() {
        return MockMvcRestDocumentation.document("api/v1/order-post",
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

    public static RestDocumentationResultHandler getAllOrder() {
        return MockMvcRestDocumentation.document("api/v1/order-get-all",
                requestParameters(
                        parameterWithName("userEmail").description("The page to retrieve")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                )
        );
    }

    public static RestDocumentationResultHandler getOneOrder() {
        return MockMvcRestDocumentation.document("api/v1/orders/{id}",
                pathParameters(
                        parameterWithName("id").description("The page to retrieve")
                )
//                ,
//                responseFields(
//                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
//                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
//                        fieldWithPath("orderDate").type(JsonFieldType.VARIES).description("orderDate valueOf date-time as String")
//                )
        );
    }

    public static RestDocumentationResultHandler deleteOrder() {
        return MockMvcRestDocumentation.document("api/v1/order-delete",
                requestParameters(
                        parameterWithName("id").description("The page to retrieve")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("orderId valueOf UUID as String"),
                        fieldWithPath("userId").type(JsonFieldType.STRING).description("userId valueOf UUID as String"),
                        fieldWithPath("orderDate").type(JsonFieldType.STRING).description("orderDate valueOf date-time as String")
                )
        );
    }

}
