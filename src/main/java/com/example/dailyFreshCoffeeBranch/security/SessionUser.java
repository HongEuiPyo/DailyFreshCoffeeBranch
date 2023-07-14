package com.example.dailyFreshCoffeeBranch.security;

import com.example.dailyFreshCoffeeBranch.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        //this.picture = member.getPicture();
    }

    public SessionUser(String email) {
        this.email = email;
    }
}
