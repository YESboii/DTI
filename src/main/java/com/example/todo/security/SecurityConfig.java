package com.example.todo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->
                {   auth.requestMatchers("/inboxmaster/auth/**").permitAll();
                    auth.requestMatchers("/inboxmaster/").permitAll();
                    auth.requestMatchers("/inboxmaster/contactus").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/inboxmaster/auth/signin")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/inboxmaster/viewtodos", true)
                                .failureUrl("/login?error=true")

                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/inboxmaster/auth/signin")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )
//                .authorizeHttpRequests().anyRequest().permitAll().and()
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService user){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(user);
        return new ProviderManager(daoAuthenticationProvider);
    }
}