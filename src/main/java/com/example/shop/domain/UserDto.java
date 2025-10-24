package com.example.shop.domain;

import lombok.Data;

import java.util.Date;
@Data
public class UserDto {

        private String username; //로그인아이디
        private String name;
        private String password;
        private String email;
        private Date created_at;
        private Date updated_at;
        private String role;

        public UserDto() {
        }

        public UserDto(String username, String name, String password, String email,String role) {
            this.username = username;
            this.name = name;
            this.password = password;
            this.email = email;
            this.role = role;
        }
    }


