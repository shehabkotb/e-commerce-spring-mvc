package com.vodafone.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;
    private CustomAuthenticationFailureHandler loginFailureHandler;
    private CustomAuthenticationSuccessHandler loginSuccessHandler;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, CustomAuthenticationFailureHandler loginFailureHandler, CustomAuthenticationSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(
                        (requests) -> requests.antMatchers("/login", "/register", "/reset", "/confirm-account/**", "/css/**", "/js/**").permitAll()
                                .antMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
                        .loginProcessingUrl("/login")
                        .failureHandler(loginFailureHandler)
                        .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );


        return http.build();
    }


//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
