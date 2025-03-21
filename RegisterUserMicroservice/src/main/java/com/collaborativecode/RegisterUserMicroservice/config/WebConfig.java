package com.collaborativecode.RegisterUserMicroservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {


    // Configuring Web to solve cors problem
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                        // addMapping() is used to map the requests
                registry.addMapping("/**")
                        // allowedOrigins is used to allow the request made by the link
                        .allowedOrigins("http://localhost:3000")
                        // allowedMethods() is used to allow what type of method are allowed for rest api
                        .allowedMethods("GET" , "POST" , "DELETE" , "OPTIONS" , "PUT")
                        // allowedHeaders is used to allow which header to access
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
