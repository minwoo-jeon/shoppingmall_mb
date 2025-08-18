package com.example.shop.controller;


import com.example.shop.domain.UserDto;
import com.example.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
            try {
                userService.signup(userDto);
                return new ResponseEntity<>("회원가입 성공",HttpStatus.CREATED); // -> 201 코드 반환 요청이 처리되어서 새로운 리소스가 생성되었다는 뜻
            }catch (IllegalStateException e) {
                //이미 존재하는 아이디가 있을경우=> 409 에러코드 반환(	서버가 요청을 수행하는 중에 충돌이 발생하였다는뜻.)
                e.printStackTrace();
                return new ResponseEntity<>("이미 존재하는 회원입니다",HttpStatus.CONFLICT);
            }catch (Exception e){
                //서버 내부 오류 =>500에러코드 반환 (서버 에러 발생)
                return new ResponseEntity<>("서버오류",HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
