package com.example.dailyFreshCoffeeBranch.config;

import com.example.dailyFreshCoffeeBranch.security.MyLoginFailureHandler;
import com.example.dailyFreshCoffeeBranch.security.MyLoginSuccessHandler;
import com.example.dailyFreshCoffeeBranch.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin()

                .and()
                    .authorizeHttpRequests()
                    .mvcMatchers("/", "/item/**","/images/**", "/cs/**", "/js/**").permitAll()
                    .antMatchers("/user/**").authenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/smallPeople/**").hasRole("SMALL_PEOPLE")
                    .anyRequest().permitAll()

                // Form Login 방식 적용
                .and()
                    .formLogin()
                    .loginPage("/members/login")
                    .loginProcessingUrl("/members/generalLogin")
                    .failureHandler(myLoginFailureHandler())
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")

                .and()
                    .logout()
                    .logoutUrl("/members/logout")
                    .logoutSuccessUrl("/members/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me", "JSESSION_ID")

                // OAuth 로그인
                .and()
                    .oauth2Login()
                    .loginPage("/members/login")
                    .defaultSuccessUrl("/")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .antMatchers("/js/**", "/css/**", "/error");
//    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new MyLoginSuccessHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyLoginFailureHandler myLoginFailureHandler() {
        return new MyLoginFailureHandler();
    }

}