package com.techeer.svproject.domain.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.address.dto.request.AddressUpdateDto;
import com.techeer.svproject.domain.address.service.AddressService;
import com.techeer.svproject.domain.user.dto.UserSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = AddressController.class)
class AddressControllerTest {

    @MockBean
    protected AddressService addressService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private AddressCreateDto addressCreateDto;
    private AddressUpdateDto addressUpdateDto;
    private UserSaveDto userSaveDto;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentationContextProvider) {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

        addressCreateDto = AddressCreateDto.builder()
                .state("state")
                .city("city")
                .zipcode(12345)
                .street("street").build();

        addressUpdateDto = AddressUpdateDto.builder()
                .addressId(UUID.randomUUID())
                .state("state")
                .city("city")
                .zipcode(12345)
                .street("street").build();

        userSaveDto = UserSaveDto.builder()
                .firstName("kim")
                .lastName("hyun")
                .email("kevinkim@naver.com")
                .password("qwer1234")
                .phoneNumber(123456789)
                .address(addressCreateDto)
                .build();


    }
    @Test
    @DisplayName("address 조회")
    void get_user_profile_success() throws Exception {
        //given
        Address givenAddress = addressCreateDto.toEntity();
        givenAddress.setAddressId(UUID.randomUUID());

        //when
        when(addressService.getAddress(any())).thenReturn(givenAddress);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/address-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userEmail", userSaveDto.getEmail()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andDo(AddressDocument.getAddress());
    }

    @Test
    void updateAddress() throws Exception {
        //given
        Address givenAddress = addressCreateDto.toEntity();
        givenAddress.setAddressId(UUID.randomUUID());

        //when
        when(addressService.updateAddress(any(),any())).thenReturn(givenAddress);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/address-list/{address-id}",UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(AddressDocument.updateAddress());
    }
}