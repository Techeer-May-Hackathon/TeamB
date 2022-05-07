package com.techeer.svproject.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.address.controller.AddressController;
import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.address.service.AddressService;
import com.techeer.svproject.domain.user.dto.UserSaveDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = AddressControllerTest.class)
@AutoConfigureRestDocs
class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;
    
    @MockBean
    protected AddressRepository addressRepository;

    @MockBean
    protected AddressService addressService;

    @MockBean
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private AddressCreateDto addressCreateDto;
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

        when(addressService.getAddress(any())).thenReturn(addressCreateDto.toEntity());
        mockMvc.perform(get("/api/v1/address-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("userEmail", userSaveDto.getEmail()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andDo(AddressDocument.getAddress());
    }

}