package com.johnhunsley.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>
 *     Access to the API via Basic authentication only. default user and pass configuration in application.properties
 * </p>
 *
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 17/11/2016
 *         Time : 14:32
 */
@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
    }
}
