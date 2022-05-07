package com.techeer.svproject.service;

import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import com.techeer.svproject.domain.user.dto.UserSaveDto;
import com.techeer.svproject.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@DisplayName("유저 서비스 테스트")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private AddressRepository addressRepository;

    private AddressCreateDto setAddressCreateDto;
    private UserSaveDto setUserDto;

    @BeforeEach
    void setUp(){

        setUserDto = UserSaveDto.builder()
                .lastName("세연")
                .firstName("최")
                .email("hi@email.com")
                .password("aa1234!")
                .phoneNumber(012453566)
                .address(setAddressCreateDto)
                .build();

        Mockito.when(userRepository.save(any())).thenReturn(setUserDto.toEntity());

    }

    @Test
    @DisplayName("유저 저장")
    void saveUser() {
        // given
        setAddressCreateDto = AddressCreateDto.builder()
                .state("kk")
                .city("안산시")
                .zipcode(34545)
                .street("선부")
                .build();

        // when
        setUserDto = UserSaveDto.builder()
                .lastName("세연")
                .firstName("최")
                .email("hi@email.com")
                .password("aa1234!")
                .phoneNumber(012453566)
                .address(setAddressCreateDto)
                .build();

        User user = userService.save(setUserDto);

        // then
        assertAll(
                ()-> assertEquals(setUserDto.getLastName(), user.getLastName()),
                ()-> assertEquals(setUserDto.getLastName(), user.getLastName()),
                ()-> assertEquals(setUserDto.getFirstName(), user.getFirstName()),
                ()-> assertEquals(setUserDto.getEmail(), user.getEmail()),
                ()-> assertEquals(setUserDto.getPassword(), user.getPassword()),
                ()-> assertEquals(setUserDto.getPhoneNumber(), user.getPhoneNumber())
        );
    }
}
