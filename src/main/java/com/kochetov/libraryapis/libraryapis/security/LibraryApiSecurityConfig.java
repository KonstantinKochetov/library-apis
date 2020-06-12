package com.kochetov.libraryapis.libraryapis.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// EnableWebSecurity annotation it applies to global web security
// WebSecurityConfigurerAdapter because we need to configure (below)
@EnableWebSecurity
public class LibraryApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LibraryApiSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.cors().and().csrf().disable().authorizeRequests()
                    // user creation no auth
                    .antMatchers(HttpMethod.POST, SecurityConstants.NEW_USER_REGISTRATION_URL).permitAll()
                    //
                    .antMatchers(HttpMethod.GET, "/v1/users/search").permitAll()
                    .antMatchers(HttpMethod.GET, "/v1/books/search").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                    // disable sessions
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // loading UserDetailsService
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
