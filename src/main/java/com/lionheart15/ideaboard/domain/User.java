package com.lionheart15.ideaboard.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User {
    @Id
    private long id;
    private String name;
    private String birth;
    private int gender;
    private String login_id;
    private String password;
    private String phone_number;
    private String email;
    private String role;
}
