package com.example.lovelypet.config;

import com.example.lovelypet.config.token.TokenFilter;
import com.example.lovelypet.config.token.TokenFilterConfigurer;
import com.example.lovelypet.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable() // ปิดการใช้งาน CSRF สำหรับความสะดวกในการทดสอบ (หากไม่จำเป็นต้องใช้)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/register","/user/login","/hotel/register","/hotel/login")
                .permitAll()
                .anyRequest().authenticated()
                .and().apply(new TokenFilterConfigurer(tokenService)); // กำหนดว่า URL อื่นๆ จะต้องมีการล็อกอินเพื่อเข้าถึง
//                .and()
//                .formLogin() // กำหนดการกำหนดค่าเกี่ยวกับแบบฟอร์มล็อกอิน
//                .loginProcessingUrl("/api/login") // กำหนด URL ที่จะใช้ส่งข้อมูลล็อกอินไปยัง
//                .usernameParameter("username") // กำหนดชื่อพารามิเตอร์ที่ใช้สำหรับช่องข้อมูลผู้ใช้
//                .passwordParameter("password") // กำหนดชื่อพารามิเตอร์ที่ใช้สำหรับช่องข้อมูลรหัสผ่าน
//                .defaultSuccessUrl("/api/login/success") // กำหนด URL หลังจากล็อกอินสำเร็จ
//                .failureUrl("/api/login/error") // กำหนด URL หากเกิดข้อผิดพลาดในการล็อกอิน
//                .and()
//                .logout() // กำหนดการกำหนดค่าเกี่ยวกับล็อกเอาท์
//                .logoutUrl("/api/logout") // กำหนด URL ที่จะใช้สำหรับการล็อกเอาท์
//                .logoutSuccessUrl("/api/logout/success") // กำหนด URL หลังจากล็อกเอาท์สำเร็จ
//                .and()
//                .exceptionHandling() // กำหนดการจัดการข้อผิดพลาด
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // กำหนดการตอบกลับเมื่อการเข้าถึงถูกปฏิเสธ
        return http.build();
    }}
