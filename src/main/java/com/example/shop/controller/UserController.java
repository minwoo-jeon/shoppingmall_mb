package com.example.shop.controller;


import com.example.shop.domain.UserDto;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final AuthenticationManager authManager;

    public UserController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    @PostMapping("/join")
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
                log.info("Exception={}", e);
                return new ResponseEntity<>("서버오류",HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpServletRequest httpRequest) {

        try {
            //AutenticationManager로 인증
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());


            Authentication authentication = authManager.authenticate(authToken);

            //인증 성공하면 SecurityContext에 저장한다
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //세션 생성
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return ResponseEntity.ok("로그인 성공");

        }catch (BadCredentialsException e){
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
