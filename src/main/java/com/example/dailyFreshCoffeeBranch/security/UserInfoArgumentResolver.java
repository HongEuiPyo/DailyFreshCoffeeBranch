package com.example.dailyFreshCoffeeBranch.security;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserInfoAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = LoginUserDto.class.equals(parameter.getParameterType());

        return isUserInfoAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LoginUserDto loginUserDto = new LoginUserDto();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            loginUserDto = new LoginUserDto(userDetails.getUsername());
        } else if (principal instanceof DefaultOAuth2User) {
            loginUserDto = (LoginUserDto) httpSession.getAttribute("user");
        }

        return loginUserDto;
    }

}