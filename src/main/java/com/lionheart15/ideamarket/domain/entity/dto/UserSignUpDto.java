package com.lionheart15.ideamarket.domain.entity.dto;

import com.lionheart15.ideamarket.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;


@AllArgsConstructor
@ToString
@Setter
@Getter
public class UserSignUpDto {

    private String name;
    private String loginId;
    private String password;
    private String phoneNumber;
    private String email;
    private String birth;
    private int gender;

}
