package com.example.smallpeopleblog.config;

import com.example.smallpeopleblog.security.MyLoginFailureHandler;
import com.example.smallpeopleblog.security.MyLoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests()
                .mvcMatchers("/", "/item/**","/images/**", "/cs/**", "/js/**").permitAll()
                .antMatchers("/user/**")
                .authenticated()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/smallPeople/**")
                .hasRole("SMALL_PEOPLE")
                .anyRequest()
                .permitAll();

        http.formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/members/generalLogin")
                .failureHandler(myLoginFailureHandler())
                .defaultSuccessUrl("/")
                .usernameParameter("email");

        http.logout()
                .logoutUrl("/members/logout")
                .logoutSuccessUrl("/members/login")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSION_ID");

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/js/**", "/css/**", "/error");
    }

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
