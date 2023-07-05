package com.example.lovelypet.business;

import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.EmaillException;
import com.example.lovelypet.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {
    private final EmailService emailService;

    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException, MessagingException {
        //prepare content(HTML)
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmaillException.templateNotFound();
        }

        String finalLink = "http://localhost:8080/activate" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);

        //prepare subject
        String subject = "Please activate your account";
        emailService.send(email, subject, html);
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));

    }
}
