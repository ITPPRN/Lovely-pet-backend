package com.example.lovelypet.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Optional<String> getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return Optional.empty();
        }
        String userId = (String) principal;

        return Optional.of(userId);
    }

    public static Optional<String> getCurrentUserRole() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        // ตรวจสอบว่า Authentication นั้นมี GrantedAuthority หรือไม่
        if (authentication.getAuthorities() == null || authentication.getAuthorities().isEmpty()) {
            return Optional.empty();
        }
        // สำหรับกรณีที่มีหลาย role (เป็นไปได้ในกรณีที่มีการกำหนดสิทธิ์หลายระดับ)
        // ในที่นี้ใช้ role แรก (index 0) ใน list เป็นค่า role ที่ใช้งาน
//        GrantedAuthority authority = (GrantedAuthority) authentication.getAuthorities();
//        return Optional.of(authority.getAuthority());

        // สำหรับกรณีที่มี role เดียว
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return Optional.of(role);
    }

    public static String generateToken() {
        List<CharacterRule> rules = Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 10),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 10),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 10));

        PasswordGenerator generator = new PasswordGenerator();

// Generated password is 12 characters long, which complies with policy
        return generator.generatePassword(30, rules);
    }
}
