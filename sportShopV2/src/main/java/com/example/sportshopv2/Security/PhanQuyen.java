package com.example.sportshopv2.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;

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
        httpSecurity.csrf(csrf -> csrf.disable()); // Tắt CSRF

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        // Quyền truy cập cho Admin
                        .requestMatchers("/khach-hang/**", "/san-pham", "/san-pham/**", "/san-pham-chi-tiet/**",
                                "/san-pham-chi-tiet", "/anh-san-pham/**", "/the-loai/**", "/nhanvien/**", "/bill/**",
                                "/ban-hang-tai-quay/**").hasAuthority("Admin")

                        // Quyền truy cập cho Staff
                        .requestMatchers("/bill/**").hasAuthority("Staff")

                        // Quyền truy cập cho Employee
                        .requestMatchers("/buy/**").hasAuthority("Employee")

                        // Cho phép truy cập công cộng
                        .requestMatchers("/login/**", "/mua-sam-SportShopV2/**", "/api/payment/**",
                                "/VNPAY-demo/**", "/images/**", "https://**","/doi-tra/**").permitAll()
                        // Các yêu cầu khác phải xác thực
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Trang đăng nhập
                        .loginPage("/login/home")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            String targetUrl;

                            // Chuyển hướng theo vai trò người dùng
                            if ("Admin".equals(role) || "Staff".equals(role)) {
                                targetUrl = "/ban-hang-tai-quay";  // Admin và Staff vào trang bán hàng tại quầy
                            } else if ("Employee".equals(role)) {
                                targetUrl = "/mua-sam-SportShopV2/trang-chu";// Employee vào trang mua sắm
                            } else {
                                targetUrl = "/ban-hang/mua-sam";  // Default page nếu không có vai trò hợp lệ
                            }

                            response.sendRedirect(targetUrl);  // Chuyển hướng người dùng
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Đường dẫn xử lý đăng xuất
                        .logoutSuccessUrl("/login/home") // Chuyển hướng sau khi đăng xuất
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/login/access"));  // Trang lỗi nếu không có quyền truy cập
        return httpSecurity.build();
    }


//    public boolean isLoggedIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null && authentication.isAuthenticated()
//                && !(authentication.getPrincipal() instanceof String
//                && authentication.getPrincipal().equals("anonymousUser"));
//    }


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
                "/bill/**",
                "/api/proxy/**",
                "/VNPAY-demo",
                "/mua-sam-SportShopV2/update-quantity"


                // Thêm các endpoint khác cần bỏ qua
        );
    }

}
