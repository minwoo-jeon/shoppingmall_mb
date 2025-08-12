package com.example.shop.mapper;

import com.example.shop.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MapperScan("com.example.shop.mapper") //mapper인터페이스 위치
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;




    @Test
    void insertUser() {
        UserDto user = new UserDto("minwoo0909","전민우","1234","minwoo@123");
        assertTrue(userMapper.insertUser(user)==1);
    }




    @Test
    void findById() {
        UserDto findUser =userMapper.findById("minwoo0909");
        assertEquals("minwoo0909",findUser.getUsername());
    }

    @Test
    void findAll() {
    }

    @Test
    void updateUser(){
        //해당 사용자의 객체를 가져온다
        UserDto findUser =userMapper.findById("minwoo0909");
        findUser.setName("김길동");
        findUser.setPassword("0000");
        findUser.setEmail("kevin15@naver.com");

        //변경하고 성공하면 1을 리턴한다
        assertTrue(userMapper.updateUser(findUser)==1);

        //잘 바뀌었는지 비교한다.
        UserDto updateUser = userMapper.findById("minwoo0909");
        assertEquals("김길동",updateUser.getName());
        assertEquals("0000",updateUser.getPassword());
        assertEquals("kevin15@naver.com",updateUser.getEmail());
    }

    @Test
    void delete(){
        String username = "minwoo0909";
        assertTrue(userMapper.deleteUser(username)==1);

    }

}