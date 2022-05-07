package com.techeer.svproject.repository;

import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("유저 저장소 테스트")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User setUser;
    private User expectUser;

    private List<User> expectList = new ArrayList<>();

    @BeforeEach
    void setup() {

        setUser = User.builder()
                .firstName("김")
                .lastName("영준")
                .email("Over@naver.com")
                .password("qwer12345!")
                .phoneNumber(1023132312)
                .address(null)
                .build();

        expectUser = userRepository.save(setUser);
        expectList.add(setUser);
    }

    @Test
    @DisplayName("유저 저장")
    void userSave() {

        // given

        // when
        User actualUser = userRepository.save(setUser);

        // then
        assertEquals(setUser, actualUser);
    }

    @Test
    @DisplayName("유저 전체 조회")
    void userFindAll() {
        // given

        // when
        List<User> actualUserList = userRepository.findAll();

        // then
        assertIterableEquals(expectList, actualUserList);
    }

    @Test
    @DisplayName("이메일 조회")
    void userFindByEmail() {
        // given
        String email = "Over@naver.com";

        // when
        User actualUser = userRepository.findByEmail(email);

        // then
        assertEquals(expectUser, actualUser);
    }

    @Test
    @DisplayName("아이디 조회")
    void UserFindById() {
        // given


        // when
        User actualUser = userRepository.findById(expectUser.getId()).get();

        // then
        assertEquals(expectUser, actualUser);
    }

    @Test
    @DisplayName("이메일 존재 여부")
    void userEmailCheck() {
        // given

        String email = "Over@naver.com";

        // when

        boolean checkValue = userRepository.existsByEmail(email);

        // then

        assertTrue(checkValue);
    }

    @Test
    @DisplayName("유저 삭제")
    void userDelete() {

        // given
        String email = "Over@naver.com";

        // when

        userRepository.delete(expectUser);
        boolean checkValue = userRepository.existsByEmail(email);

        // then
        assertFalse(checkValue);
    }
}