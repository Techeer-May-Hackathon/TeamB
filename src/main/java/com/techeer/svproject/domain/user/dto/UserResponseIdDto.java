package com.techeer.svproject.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
public class UserResponseIdDto {
    private UUID id;

    public static UserResponseIdDto fromEntity(UUID id){
        return UserResponseIdDto.builder()
                .id(id)
                .build();
    }
}
