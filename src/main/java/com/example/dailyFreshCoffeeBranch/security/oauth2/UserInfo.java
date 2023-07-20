package com.example.dailyFreshCoffeeBranch.security.oauth2;

import com.example.dailyFreshCoffeeBranch.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserInfo implements Serializable {

    private String name;
    private String email;
    private String picture;

    public UserInfo(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        //this.picture = member.getPicture();
    }

    public UserInfo(String email) {
        this.email = email;
    }
}
