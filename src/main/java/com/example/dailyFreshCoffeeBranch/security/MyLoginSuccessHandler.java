package com.example.dailyFreshCoffeeBranch.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        log.warn("Login Success");

        HttpSession session = request.getSession();
        Principal principal = (Principal) auth.getPrincipal();
        session.setAttribute("user", new SessionUser(principal.getName()));

        List<String> roleNames = new ArrayList<>();

        auth.getAuthorities().forEach(authority -> {
            roleNames.add(authority.getAuthority());
        });

        log.warn("ROLE NAMES: " + roleNames);

        if (roleNames.contains("ROLE_ADMIN")) {
            response.sendRedirect("/sample/admin");
            return;
        }

        if (roleNames.contains("ROLE_MEMBER")) {
            response.sendRedirect("/sample/member");
            return;
        }

        response.sendRedirect("/");
    }
}
