package com.johnhunsley.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 12/01/2017
 *         Time : 16:55
 */
@Configuration
@EnableWebMvc
public class ApiConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "OPTIONS", "GET", "POST", "HEAD");
    }

}
