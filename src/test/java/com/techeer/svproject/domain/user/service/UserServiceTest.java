package com.techeer.svproject.domain.user.service;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.address.dto.request.AddressCreateDto;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import com.techeer.svproject.domain.user.dto.UserRequestUpdateDto;
import com.techeer.svproject.domain.user.dto.UserResponseDto;
import com.techeer.svproject.domain.user.dto.UserSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    private UserRequestUpdateDto userRequestUpdateDto;

    private AddressCreateDto setAddressCreateDto;
    private UserSaveDto setUserDto;
    private UserResponseDto setUserResponseDto;
    private Address setaddress;

    private final List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        UUID addressId = new UUID(10l, 10l);

        User user = new User(addressId, "세연", "최", "hi@email.com", "aa1234!", 012453566, Address.builder().build(), null);

        setaddress = Address.builder()
                .state("ff")
                .city("안산시")
                .street("선부")
                .zipcode(123)
                .build();

        setUserDto = UserSaveDto.builder()
                .lastName("최")
                .firstName("세연")
                .email("hi@email.com")
                .password("aa1234!")
                .phoneNumber(012453566)
                .address(setAddressCreateDto)
                .build();

        setUserResponseDto = UserResponseDto.builder()
                .id(addressId)
                .firstName("세연")
                .lastName("최")
                .email("hi@email.com")
                .phoneNumber(012453566)
                .address(setaddress)
                .build();

        userList.add(user);

        Mockito.when(userRepository.save(any())).thenReturn(setUserDto.toEntity());
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Mockito.when(userRepository.findByEmail(any())).thenReturn(user);
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(true);
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

        setUserDto = UserSaveDto.builder()
                .lastName("최")
                .firstName("세연")
                .email("hi@email.com")
                .password("aa1234!")
                .phoneNumber(012453566)
                .address(setAddressCreateDto)
                .build();

        // when

        User user = userService.save(setUserDto);

        // then
        assertAll(
                () -> assertEquals(setUserDto.getLastName(), user.getLastName()),
                () -> assertEquals(setUserDto.getLastName(), user.getLastName()),
                () -> assertEquals(setUserDto.getFirstName(), user.getFirstName()),
                () -> assertEquals(setUserDto.getEmail(), user.getEmail()),
                () -> assertEquals(setUserDto.getPassword(), user.getPassword()),
                () -> assertEquals(setUserDto.getPhoneNumber(), user.getPhoneNumber())
        );
    }

    @Test
    @DisplayName("모든 유저 찾기")
    void userFindAll() {
        // given
        List<UserResponseDto> expectList = new ArrayList<>();
        expectList.add(setUserResponseDto);

        // when

        List<UserResponseDto> actualUserList = userService.findAll();

        // then
//        Assertions.assertThat(expectListpectList, actualUserList);
        assertAll(
                () -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getLastName(), actualUser.getLastName())),
                () -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getFirstName(), actualUser.getFirstName())),
                //() -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getAddress(), actualUser.getAddress())),
                () -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getEmail(), actualUser.getEmail())),
                () -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getPhoneNumber(), actualUser.getPhoneNumber())),
                () -> actualUserList.forEach(actualUser -> assertEquals(setUserResponseDto.getId(), actualUser.getId()))
        );
    }

    @Test
    @DisplayName("이메일로 해당 유저 찾기")
    void userFindByEmail() {
        // given
        String email = "hi@email.com";

        // when
        UserResponseDto actualUser = userService.findByEmail(email);

        // then
        assertAll(
                () -> assertEquals(setUserResponseDto.getLastName(), actualUser.getLastName()),
                () -> assertEquals(setUserResponseDto.getLastName(), actualUser.getLastName()),
                () -> assertEquals(setUserResponseDto.getFirstName(), actualUser.getFirstName()),
                () -> assertEquals(setUserResponseDto.getEmail(), actualUser.getEmail()),
                () -> assertEquals(setUserResponseDto.getPhoneNumber(), actualUser.getPhoneNumber())
        );
    }

    @Test
    @DisplayName("아이디로 해당 유저 찾기")
    void userFindById() {
        // given
        UUID addressId = new UUID(10l, 10l);
        // when
        User user = userService.findById(addressId);

        // then
        assertAll(
                () -> assertEquals(setUserResponseDto.getLastName(), user.getLastName()),
                () -> assertEquals(setUserResponseDto.getLastName(), user.getLastName()),
                () -> assertEquals(setUserResponseDto.getFirstName(), user.getFirstName()),
                () -> assertEquals(setUserResponseDto.getEmail(), user.getEmail()),
                () -> assertEquals(setUserResponseDto.getPhoneNumber(), user.getPhoneNumber())
        );
    }

    @Test
    @DisplayName("해당 이메일 존재여부 테스트")
    void emailCheckDuplicate() {
        // given
        String email = "hi@email.com";

        // when
        boolean actualcheck = userRepository.existsByEmail(email);

        // then
        assertTrue(actualcheck);
    }

    void userDelete(){

        userService.delete("hi@email.com");
    }

    @Test
    @DisplayName("해당 이메일 삭제")
    void userEmailDelete() {
        // given

        // when

        // then
        assertDoesNotThrow(this::userDelete);
    }

    @Test
    @DisplayName("유저 업데이트")
    void userUpdate() {
        // given
        String email = "hi@email.com";
        UUID addressId = new UUID(10l, 10l);

        UserRequestUpdateDto userRequestUpdateDto = new UserRequestUpdateDto("최", "세연", "aa1234!", 012453566, Address.builder().build());

        // when
        UUID userId = userService.update(email, userRequestUpdateDto);

        // then
        assertEquals(userId, addressId);
    }
}


