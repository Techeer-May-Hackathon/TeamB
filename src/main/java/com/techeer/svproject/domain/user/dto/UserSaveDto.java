package com.techeer.svproject.domain.user.dto;

import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveDto {
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private Integer phoneNumber;
    private AddressCreateDto address;

    public User toEntity() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
