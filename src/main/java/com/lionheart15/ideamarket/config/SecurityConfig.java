package com.lionheart15.ideamarket.config;

import com.lionheart15.ideamarket.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPwd());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()       // csrf 토큰 비활성화
                .authorizeRequests()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()                    // form login
                .loginPage("/users/login")      // 로그인 페이지
                .usernameParameter("loginId")   // 로그인에 사용될 id
                .passwordParameter("password")  // 로그인에 사용될 password
                .defaultSuccessUrl("/")         // 로그인 성공 시 redirect 될 URL
                .failureUrl("/users/login")    // 로그인 실패 시 redirect 될 URL
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
        ;

        // 중복 로그인 방지
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);
    }
}
