package com.example.dailyFreshCoffeeBranch.com;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
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
        boolean isUserClass = UserInfoDto.class.equals(parameter.getParameterType());

        return isUserInfoAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfoDto userInfoDto = new UserInfoDto();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userInfoDto = new UserInfoDto(userDetails.getUsername());
        } else if (principal instanceof DefaultOAuth2User) {
            userInfoDto = (UserInfoDto) httpSession.getAttribute("user");
        }

        return userInfoDto;
    }

}