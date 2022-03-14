package com.sysco.miniproject.config;


import com.sysco.miniproject.security.jwt.AuthEntryPointJwt;
import com.sysco.miniproject.security.jwt.AuthTokenFilter;
import com.sysco.miniproject.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import static com.sysco.miniproject.security.ApiSpec.NonSecuredGetApis;
import static com.sysco.miniproject.security.ApiSpec.NonSecuredPostApis;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final  UserDetailsServiceImpl userDetailsService;


    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, NonSecuredPostApis.get())
                .permitAll()
                .antMatchers(HttpMethod.GET, NonSecuredGetApis.get())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        http.addFilterBefore(authenticationJwtTokenFilter(), SecurityContextHolderAwareRequestFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v3/api-docs/**",
                "/bus/v3/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**");
    }
}

