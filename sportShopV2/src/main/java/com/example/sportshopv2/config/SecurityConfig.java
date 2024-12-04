//package com.example.sportshopv2.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurer {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().configurationSource(request -> {
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:8081")); // Chỉ định các nguồn cụ thể
//                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    config.setAllowCredentials(true); // Cho phép gửi thông tin xác thực
//                    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
//                    return config;
//                })
//                .and()
//                .csrf().disable() // Nếu sử dụng SockJS, nên tắt CSRF
//                .authorizeRequests()
//                .anyRequest().authenticated(); // Cấu hình bảo mật cho các endpoint khác
//    }
//}
