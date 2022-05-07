package com.techeer.svproject.domain.user.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import com.techeer.svproject.domain.user.dto.UserRequestUpdateDto;
import com.techeer.svproject.domain.user.dto.UserResponseDto;
import com.techeer.svproject.domain.user.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UUID save(UserSaveDto userSaveDto) {
        String encodedPassword = Hashing.sha256().hashString(userSaveDto.getPassword(), StandardCharsets.UTF_8).toString();

        User user = User.builder()
                .firstName(userSaveDto.getFirstName())
                .lastName(userSaveDto.getLastName())
                .email(userSaveDto.getEmail())
                .phoneNumber(userSaveDto.getPhoneNumber())
                .password(encodedPassword)
                .build();
        user.setAddress(userSaveDto.getAddress().toEntity());
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserResponseDto.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public User findById(UUID userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }


    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public void delete(String email) {
        User user = userRepository.findByEmail(email);

        userRepository.delete(user);
    }

    public UUID update(String email, UserRequestUpdateDto requestDto){
        User user = userRepository.findByEmail(email);
        user.update(requestDto.getLastName(), requestDto.getFirstName(),
                requestDto.getPassword(), requestDto.getPhoneNumber(),
                requestDto.getAddress());
        userRepository.save(user);
        return user.getId();

    }
}
