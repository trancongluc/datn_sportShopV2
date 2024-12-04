package com.example.sportshopv2.sercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class PhanQuyen {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
                "SELECT tk.username, tk.password, 1 as enabled FROM Account tk WHERE tk.username = ? AND tk.status = 'Active' AND deleted = 0;\n"
        );

        manager.setAuthoritiesByUsernameQuery(
                "SELECT tk.username, tk.role FROM Account tk WHERE tk.username = ?"
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/khach-hang/**", "/san-pham", "/san-pham/**", "/san-pham-chi-tiet/**",
                                "/san-pham-chi-tiet", "/anh-san-pham/**", "/the-loai/**","/dot-giam-gia/**",
                                "/nhanvien/**", "/bill/**", "/ban-hang-tai-quay/**", "/giam-gia/**,/doi-tra/**").hasAuthority("Admin")
                        .requestMatchers("/bill/**", "/buy/**").hasAuthority("Staff")
                        .requestMatchers("/buy/**").hasAuthority("Employee")
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login/home")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            String targetUrl;
                            if ("Admin".equals(role) || "Staff".equals(role)) {
                                targetUrl = "/giam-gia/view";
                            } else {
                                targetUrl = "/giam-gia/view";
                            }
                            response.sendRedirect(targetUrl);
                        })
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/login/access"));
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/chat-lieu/**",
                "/the-loai/**",
                "/san-pham/**",
                "/san-pham-chi-tiet/**",
                "/co-giay/**",
                "/de-giay/**",
                "/thuong-hieu/**",
                "/mau-sac/**",
                "/kich-thuoc/**",
                "/anh-san-pham/**",
                "/ban-hang-tai-quay/**",
                "/ban-hang-tai-quay/update-hoa-don/**",
                "/khach-hang/**",
                "/nhanvien/**",
                "/hoa-don/**",
                "/hoa-don-chi-tiet/**",
                "khach-hang/thong-tin-khach-hang/**"

                // Thêm các endpoint khác cần bỏ qua
        );
    }

}