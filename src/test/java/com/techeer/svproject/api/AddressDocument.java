package com.techeer.svproject.api;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public class AddressDocument {
    public static RestDocumentationResultHandler getAddress() {

        return document("api/v1/address-list",
                requestParameters(
                    parameterWithName("page").description("The page to retrieve")
                ),
                requestFields(
                        fieldWithPath("addressId").type(JsonFieldType.STRING).description("addressId"),
                        fieldWithPath("state").type(JsonFieldType.STRING).description("state"),
                        fieldWithPath("city").type(JsonFieldType.STRING).description("city"),
                        fieldWithPath("street").type(JsonFieldType.STRING).description("street"),
                        fieldWithPath("zipcode").type(JsonFieldType.NUMBER).description("zip code")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 칵테일 id")
                ));
    }

}
