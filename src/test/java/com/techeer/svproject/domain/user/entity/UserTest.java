package com.techeer.svproject.domain.user.entity;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {


    private User user = new User();
    private User setUser;
    private Address setAddress;
    
    @Test
    @DisplayName("유저 업데이트")
    void userUpdate() {
        // given
        setAddress = Address.builder()
                .state("ff")
                .city("안산시")
                .street("선부")
                .zipcode(123)
                .build();
        // when

        user.update("최", "세연", "1234!", 123456789, setAddress);

        // then
        assertAll(
                () -> assertEquals("최", user.getLastName()),
                () -> assertEquals("세연", user.getFirstName()),
                () -> assertEquals("1234!", user.getPassword()),
                () -> assertEquals(123456789, user.getPhoneNumber()),
                () -> assertEquals(setAddress, user.getAddress())
        );
    }

}
