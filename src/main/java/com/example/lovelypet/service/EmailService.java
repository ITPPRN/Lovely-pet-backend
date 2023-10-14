package com.example.lovelypet.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {

    @Value("${app.email.from}")
    private String from;

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true indicates HTML
        javaMailSender.send(message);
        log.info("Mock Send Email");

    }
}
//import jakarta.mai l.internet.MimeMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void send(String to,String subject,String html){
//        MimeMessagePreparator message = new MimeMessagePreparator() {
//            @Override
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//
//            }
//        };
//        mailSender.send(message);
//    }
//}
