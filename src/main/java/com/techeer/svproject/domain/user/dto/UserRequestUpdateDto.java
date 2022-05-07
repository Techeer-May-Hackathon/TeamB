package com.techeer.svproject.domain.user.dto;

import com.techeer.svproject.domain.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserRequestUpdateDto {
    private String firstName;
    private String lastName;
    private String password;
    private int phoneNumber;
    private Address address;

    public UserRequestUpdateDto(String firstName, String lastName, String password ,int phoneNumber, Address address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
