package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class LoginUserDto implements Serializable {

    private String name;
    private String email;
    private String picture;

    public LoginUserDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        //this.picture = member.getPicture();
    }

    public LoginUserDto(String email) {
        this.email = email;
    }
}
