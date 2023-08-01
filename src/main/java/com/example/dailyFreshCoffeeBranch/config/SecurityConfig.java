package com.example.dailyFreshCoffeeBranch.config;

import com.example.dailyFreshCoffeeBranch.security.MyLoginFailureHandler;
import com.example.dailyFreshCoffeeBranch.security.MyLoginSuccessHandler;
import com.example.dailyFreshCoffeeBranch.security.oauth2.CustomAuthorizationRequestResolver;
import com.example.dailyFreshCoffeeBranch.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Slf4j
public class SecurityConfig {

    private final DataSource dataSource;
    private final ClientRegistrationRepository clientRegistrationRepository;
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
//                    .defaultSuccessUrl("/")
                    .successHandler(myLoginSuccessHandler())
                    .failureHandler(myLoginFailureHandler())
                    .usernameParameter("email")

                .and()
                    .logout()
                    .logoutUrl("/members/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me", "JSESSION_ID")
                    .logoutSuccessUrl("/members/login")

                .and()
                    .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .alwaysRemember(false)

                // OAuth 로그인
                .and()
                    // Customizing the Authorization Request
                    .oauth2Login(oauth2Login ->
                            oauth2Login
                                    .authorizationEndpoint(authorizationEndpoint ->
                                                    authorizationEndpoint
                                                            .authorizationRequestResolver(
                                                                    new CustomAuthorizationRequestResolver(
                                                                            this.clientRegistrationRepository)
                                                            )
                                                    )
                                    )

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
    public AuthenticationSuccessHandler myLoginSuccessHandler() {
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

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}