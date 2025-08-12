package com.example.shop.controller;


import com.example.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {


    private final  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/join")
//    public ResponseEntity<String> processJoin(UserDto userDto, Model model) {
//        //일단 회원이 존재하는지 찾는다
//        String userEmail = userService.findByEmail(userDto.getEmail());
//        if(userEmail != null){
//            System.out.println(" 이미 존재하는 회원입니다");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 회원입니다");
//        }
//        //없을경우 저장
//        userService.save(userDto);
//        return ResponseEntity.ok("회원가입 성공");
//    }
}
