package com.tomspencerlondon.moviebooker.moviegoer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/moviegoer/register/**")
                                .permitAll()
                                .requestMatchers(
                                        "/js/**",
                                        "/css/**",
                                        "/img/**", "/admin/**")
                                .permitAll()
                                .requestMatchers("/moviegoer/**")
                                .hasRole("USER")
                ).formLogin(
                        form -> form
                                .loginPage("/moviegoer/login")
                                .loginProcessingUrl("/moviegoer/login")
                                .successHandler(movieGoerSuccessHandler())
                                .failureUrl("/moviegoer/login?error=true")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );


        http.headers().frameOptions().disable();
        http.exceptionHandling().accessDeniedPage("/moviegoer/403");
        return http.build();
    }

    private AuthenticationSuccessHandler movieGoerSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/moviegoer/bookings");
        };
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .successHandler(adminSuccessHandler())
                .failureUrl("/admin/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/")
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/admin/403");
        return http.build();
    }

    private AuthenticationSuccessHandler adminSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/admin/movie-programs");
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
