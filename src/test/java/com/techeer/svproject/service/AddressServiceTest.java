package com.techeer.svproject.service;


import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.address.dto.request.AddressUpdateDto;
import com.techeer.svproject.domain.address.service.AddressService;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    private Address givenAddress;

    private User givenUser;

    private Address address1;

    @BeforeEach
    void setUp(){
        UUID addressId = new UUID(10l, 10l);

        givenAddress = Address.builder()
                .state("CF")
                .city("city")
                .street("street")
                .zipcode(123456)
                .build();

        givenAddress.setAddressId(addressId);

        givenUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(givenAddress)
                .build();

        when(addressRepository.getById(any())).thenReturn(givenAddress);
        when(addressRepository.save(any())).thenReturn(givenAddress);
        when(userRepository.findByEmail(any())).thenReturn(givenUser);
    }

    @Test
    @DisplayName("create 주소")
    void createAddress() {
        // given

        // when
        AddressCreateDto addressCreateDto = AddressCreateDto.builder()
                .state("CF")
                .city("city")
                .street("street")
                .zipcode(123456)
                .build();
        UUID addressId = addressService.createAddress(addressCreateDto);
        // then
        assertAll(
                () -> assertEquals(addressId,givenAddress.getAddressId())
        );

    }

    @Test
    @DisplayName("update 주소")
    void updateAddress() {
        // given

        // when
        UUID addressId = new UUID(10l, 10l);

        AddressUpdateDto addressUpdateDto = AddressUpdateDto.builder()
                .addressId(addressId)
                .state("FF")
                .city("안산시")
                .street("어딘가")
                .zipcode(1234566)
                .build();

        Address address = addressService.updateAddress(addressId, addressUpdateDto);

        // then
        assertAll(
                () -> assertEquals(address.getAddressId(), addressId)
        );
    }

    @Test
    @DisplayName("get adress")
    void getAddress() {
        // given

        // when
        String email = "user@naver.com";
        User user = userRepository.findByEmail(email);
        Address address = user.getAddress();
        // then
        assertAll(
                () -> assertEquals(address,givenUser.getAddress())
        );
    }

    @Test
    @DisplayName("get address2")
    void getAddress2() {
        // given

        // when
        String email = "user@email.com";
        Address realAddress = addressService.getAddress(email);
        // then
        assertEquals(realAddress, givenUser.getAddress());
    }



}
