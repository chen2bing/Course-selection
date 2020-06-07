package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    //未登录时允许访问的路径
                    .antMatchers("/login", "/js/*", "/css/**", "/img/**", "/checkcode", "/register").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    //登录界面
                    .loginPage("/login")
                    .permitAll()
                    //登录成功后访问的url
                    .defaultSuccessUrl("/who", true)
                    .and()
                .logout()
                    //退出登录时清除cookies
                    .deleteCookies("remove")
                    .invalidateHttpSession(true)
                    //退出登录的url
                    .logoutUrl("/logout")
                    //退出系统后返回初始登录界面
                    .logoutSuccessUrl("/login")
                    .permitAll();

        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }
    /*
    * 重定义登录的验证方式
    * */
    @Autowired
    private AuthJdbcProvider authProvider;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

}