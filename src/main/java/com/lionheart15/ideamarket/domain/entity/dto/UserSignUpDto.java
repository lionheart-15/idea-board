package com.lionheart15.ideamarket.domain.entity.dto;

import com.lionheart15.ideamarket.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;


@ToString
public class UserSignUpDto {


    public UserSignUpDto(String name, String loginId, String password, String phoneNumber, String email, String birth,int gender) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.gender=gender;
    }

    private String name;
    private String loginId;
    private String password;
    private String phoneNumber;
    private String email;
    private String birth;
    private String role;
    private int gender;


    public User toEntity(){
        return new User(name,birth,loginId,password,phoneNumber,email,role,gender);
    }
}
