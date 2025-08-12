package com.example.shop.mapper;

import java.util.*;
import com.example.shop.domain.UserDto;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MapperScan("com.example.shop.mapper") //mapper인터페이스 위치
@Transactional //테서드 메서드 실행후 자동 롤백
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private String testUsername; //테스트할 회원아이디

    @BeforeEach
    void setUp(){
        testUsername = "testUser_" + UUID.randomUUID();
        UserDto userDto = new UserDto(testUsername , "테스트" , "1234" ,testUsername+"@test.com");
        userMapper.insertUser(userDto);
    }


    @Test
    void insertUserTest() {
            String username = "insertUser+" + UUID.randomUUID();
            UserDto user = new UserDto(username,"이름","pw",username+"@email.cm");
            assertEquals(1,userMapper.insertUser(user));
        }



    @Test
    void findByIdTest() {
        UserDto findUser =userMapper.findById(testUsername);
        assertEquals(testUsername,findUser.getUsername());
    }



    @Test
    void updateUserTest(){
        //해당 사용자의 객체를 가져온다
        UserDto updateUser =userMapper.findById(testUsername);
        updateUser.setName("김길동");
        updateUser.setPassword("0000");
        updateUser.setEmail("kevin15@naver.com");


        //변경하고 성공하면 1을 리턴한다
        assertEquals(1,userMapper.updateUser(updateUser));

        //잘 바뀌었는지 비교한다.
        UserDto findUpdateUser  = userMapper.findById(testUsername);
        assertEquals("김길동",findUpdateUser.getName());
        assertEquals("0000",findUpdateUser.getPassword());
        assertEquals("kevin15@naver.com",findUpdateUser.getEmail());
    }


    @Test
    void deleteTest() {
        assertEquals(1,userMapper.deleteUser(testUsername) );
        assertNull(userMapper.findById(testUsername));

    }

}