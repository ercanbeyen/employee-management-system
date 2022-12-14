package com.ercanbeyen.employeemanagementsystem.security.config;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.security.filter.CustomAuthenticationFilter;
import com.ercanbeyen.employeemanagementsystem.security.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/login/**", "/token/refresh/**")
                .permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/employees/**", "/departments/**", "/jobTitles/**", "/salaries/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/employees/**", "/departments/**", "/jobTitles/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,
                        "/employees/**", "/departments/**", "/jobTitles/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/employees/**/salary", "/employees/salaries/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/employees/**/details")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/departments/**", "/jobTitles/**", "/employees/**/role")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/employees/**/profession")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/statistics/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/tickets/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/tickets/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/tickets/**/reopen")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/tickets/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,
                        "/tickets/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/payments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/payments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/payments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,
                        "/payments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN));

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/comments/**")
                        .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/comments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/comments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,
                        "/comments/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER), String.valueOf(Role.USER));

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/addresses/**")
                .hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER));

        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        http.addFilter(customAuthenticationFilter);

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
