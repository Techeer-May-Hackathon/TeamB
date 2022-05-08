package com.techeer.svproject.domain.user.dto;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    private Address setAddress;

    @Test
    @DisplayName("")
    void userRequestUpdateDto() {
        // given
        setAddress = Address.builder()
                .state("ff")
                .city("안산시")
                .street("선부")
                .zipcode(123)
                .build();
        // when
        UserRequestUpdateDto userRequestUpdateDto =
                new UserRequestUpdateDto("세연", "최", "1234!", 123456789, setAddress);

        // then
        assertAll(
                () -> assertEquals("최", userRequestUpdateDto.getLastName()),
                () -> assertEquals("세연", userRequestUpdateDto.getFirstName()),
                () -> assertEquals("1234!", userRequestUpdateDto.getPassword()),
                () -> assertEquals(123456789, userRequestUpdateDto.getPhoneNumber()),
                () -> assertEquals(setAddress, userRequestUpdateDto.getAddress())
        );
    }

    @Test
    @DisplayName("")
    void userResponseDto() {
        // given
        UUID addressId = new UUID(10l, 10l);

        setAddress = Address.builder()
                .state("ff")
                .city("안산시")
                .street("선부")
                .zipcode(123)
                .build();
        User user = new User(addressId, "세연", "최", "hi@email.com", "aa1234!", 012453566, setAddress, null);

        // when
        UserResponseDto userResponseDto =
                new UserResponseDto(user);

        // then
        assertAll(
                () -> assertEquals("최", userResponseDto.getLastName()),
                () -> assertEquals("세연", userResponseDto.getFirstName()),
                () -> assertEquals("hi@email.com", userResponseDto.getEmail()),
                () -> assertEquals(012453566, userResponseDto.getPhoneNumber()),
                () -> assertEquals(setAddress, userResponseDto.getAddress())
        );
    }


}
