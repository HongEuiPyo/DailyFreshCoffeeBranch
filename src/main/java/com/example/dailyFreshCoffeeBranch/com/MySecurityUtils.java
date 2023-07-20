package com.example.dailyFreshCoffeeBranch.com;

import com.example.dailyFreshCoffeeBranch.security.oauth2.SessionUser;

import javax.servlet.http.HttpSession;
import java.security.Principal;

public class MySecurityUtils {

    public static String findMemberEmail(Principal principal, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        return user == null ?
                principal.getName() :
                user.getEmail();
    }

}
