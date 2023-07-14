package com.example.dailyFreshCoffeeBranch.com;

import com.example.dailyFreshCoffeeBranch.security.socialLogin.SessionUser;

import javax.servlet.http.HttpSession;
import java.security.Principal;

public class MySecurityUtils {

    public static String getTrueMemberEmail(Principal principal, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        return user == null ?
                principal.getName() :
                user.getEmail();
    }

}
