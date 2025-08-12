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

        public UserDto() {
        }

        public UserDto(String username, String name, String password, String email) {
            this.username = username;
            this.name = name;
            this.password = password;
            this.email = email;
        }
    }


