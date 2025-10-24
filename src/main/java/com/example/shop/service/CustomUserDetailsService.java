package com.example.shop.service;

import com.example.shop.domain.CustomUserDetails;
import com.example.shop.domain.UserDto;
import com.example.shop.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userData =  userMapper.findById(username);

        if (username != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
