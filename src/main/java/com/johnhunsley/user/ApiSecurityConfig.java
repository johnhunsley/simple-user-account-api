package com.johnhunsley.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svlada.security.RestAuthenticationEntryPoint;
import com.svlada.security.auth.ajax.AjaxAuthenticationProvider;
import com.svlada.security.auth.ajax.AjaxLoginProcessingFilter;
import com.svlada.security.auth.jwt.JwtAuthenticationProvider;
import com.svlada.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.svlada.security.auth.jwt.SkipPathRequestMatcher;
import com.svlada.security.auth.jwt.extractor.TokenExtractor;
import com.svlada.security.crypto.password.StandardPasswordBase64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Access to the API via Basic authentication only. default user and pass configuration in application.properties
 * </p>
 * <p>
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 17/11/2016
 *         Time : 19:32
 */
@Configuration
@ComponentScan({"com.svlada","com.johnhunsley.user"})
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/user/**";
    public static final String ROLE_ENTRY_POINT = "/role/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/token";

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private TokenExtractor tokenExtractor;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private ObjectMapper objectMapper;

    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    protected StandardPasswordBase64Encoder passwordEncoder() {
        return new StandardPasswordBase64Encoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    /**
     * todo - managment of users need to be authorized by a specific role here and at the {@link com.johnhunsley.user.service.UserDetailsServiceImpl} level
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
        http
        .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
           .authorizeRequests()
        .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
        .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point

        .and()
                .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
                .antMatchers(HttpMethod.PUT, ROLE_ENTRY_POINT).hasRole("ADMIN")

        .and()
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
