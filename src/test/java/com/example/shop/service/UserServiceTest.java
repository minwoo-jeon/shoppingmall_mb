package com.example.shop.service;

import com.example.shop.domain.UserDto;
import com.example.shop.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserMapper mapper;

    public String testUserName="";

//    @BeforeEach
//    void setUp() {
//        testUserName = "testUser_" + UUID.randomUUID();
//        UserDto userDto = new UserDto(testUserName , "테스트" , "1234" ,testUserName+"@test.com");
//        mapper.insertUser(userDto);
//    }
//
//
//
//    //회원가입 실패 로직
//    @Test
//    void signupFailTest(){
//         UserDto userDto = new UserDto(testUserName , "테스트" , "1234" ,testUserName+"@test.com");
//        assertThatThrownBy(()-> userService.signup(userDto)).
//                isInstanceOf(IllegalStateException.class).
//                hasMessage("이미 존재하는 회원입니다");
//    }
//
//    //회원가입 성공 로직
//    @Test
//    void signupSuccessTest(){
//        testUserName = "test" +UUID.randomUUID();
//        UserDto newUser = new UserDto(testUserName , "테스트" , "1234" ,testUserName+"@test.com");
//          userService.signup(newUser);
//
//          UserDto findUser = userService.findById(newUser.getUsername());
//          log.info("findUser={}" , findUser);
//          assertThat(findUser).isNotNull();
//          assertThat(findUser.getUsername()).isEqualTo(newUser.getUsername());
//          assertThat(findUser.getPassword()).isNotEqualTo("1234");
//    }



}