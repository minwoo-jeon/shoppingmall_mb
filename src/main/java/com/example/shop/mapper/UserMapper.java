package com.example.shop.mapper;

import java.util.*;

import com.example.shop.domain.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper //스프링이 mybatis mapper를 인식해서 프록시
public interface UserMapper {
    int insertUser(UserDto user); //삽입
    UserDto findById(String login_id); //조회

    int updateUser(UserDto user);// 수정

    int deleteUser(String  username); //삭제



}
