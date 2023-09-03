//package com.example.lovelypet.business;
//
//import com.example.lovelypet.business.EmailBusiness;
//import com.example.lovelypet.exception.BaseException;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class TestEmailBusiness {
//
//    @Autowired
//    private EmailBusiness emailBusiness;
//
//
//    @Order(1)
//    @Test
//    void testSendActivateEmail() throws BaseException, MessagingException {
//        emailBusiness.sendActivateUserEmail(
//                TestData.email,
//                TestData.name,
//                TestData.token
//        );
//    }
//
//    interface TestData {
//        String email = "ittipol0923631832@gmail.com";
//
//        String name = "Ittipol";
//
//        String token = "m#@:LSEREWELF09sdfsdf";
//    }
//}
//
