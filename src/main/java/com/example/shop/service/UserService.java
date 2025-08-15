package com.example.shop.service;

import com.example.shop.domain.UserDto;
import com.example.shop.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserMapper mapper,PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    public int signup(UserDto user){
        
        //1. 중복체크 (예: 회원이이디)
        if (mapper.findById(user.getUsername()) != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        //2. 비밀번호 해싱
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        //3. 회원 저장
        return mapper.insertUser(user);
    }


    //회원 상세정보 정보 조회
    public UserDto findById(String username){
        return mapper.findById(username);
    }


    //업데이트
    public int updateUser(UserDto user){
        //비밀번호가 변경될떄만
        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            String updatePw = user.getPassword();
            String encode =passwordEncoder.encode(updatePw);
            user.setPassword(encode);
        }
        return mapper.updateUser(user);
    }

    //삭제
    public int deleteUser(String username){
        return mapper.deleteUser(username);
    }
}
