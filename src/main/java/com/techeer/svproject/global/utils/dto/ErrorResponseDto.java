package com.techeer.svproject.global.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private String status;

    public static ErrorResponseDto fromEntity(String status, String message){
        return ErrorResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
