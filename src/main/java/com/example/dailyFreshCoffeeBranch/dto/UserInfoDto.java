package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class UserInfoDto implements Serializable {

    private String name;
    private String email;
    private String picture;

    public UserInfoDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        //this.picture = member.getPicture();
    }

    public UserInfoDto(String email) {
        this.email = email;
    }
}
