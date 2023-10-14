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


    final public EmailService emailService;

    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException, MessagingException {

        //prepare content(HTML)
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome to Backend</title>
                </head>
                <body>
                <h1>Hello, ${P_NAME}</h1>
                <h2>Please activate your account</h2>
                <p>Click <a href="${P_LINK}"> here</a> </p>
                </body>
                </html>""";
//        try {
//            html = readEmailTemplate("email-activate-user.html");
//        } catch (IOException e) {
//            throw EmaillException.templateNotFound();
//        }


        //String ip = "";
        //https://backend-application.pcnone.com
        String finalLink = "https://backend-application.pcnone.com/user/activate?request=" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        //prepare subject
        String subject = "Please activated your account";

        emailService.send(email, subject, html);


    }


    public void sendActivateHotelEmail(String email, String name, String token) throws BaseException, MessagingException {

        //prepare content(HTML)
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome to Backend</title>
                </head>
                <body>
                <h1>Hello, ${P_NAME}</h1>
                <h2>Please activate your account</h2>
                <p>Click <a href="${P_LINK}"> here</a> </p>
                </body>
                </html>""";
//        try {
//            html = readEmailTemplate("email-activate-hotel.html");
//        } catch (IOException e) {
//            throw EmaillException.templateNotFound();
//        }

        //String ip = "";

        //http://192.168.105.124:8080
        String finalLink = "https://backend-application.pcnone.com/hotel/activate?request=" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);


        //prepare subject
        String subject = "Please activated your account";

        emailService.send(email, subject, html);


    }

    public void sendResetPasswordEmail(String email, String name, String token) throws BaseException, MessagingException {

        //prepare content(HTML)
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome to Backend</title>
                </head>
                <body>
                <h1>Hello, ${P_NAME}</h1>
                <h2>Password has been changed</h2>
                <p>If you are not the operator Click <a href="${P_LINK}"> here</a> </p>
                </body>
                </html>""";
//        try {
//            html = readEmailTemplate("email-reset-password-user.html");
//        } catch (IOException e) {
//            throw EmaillException.templateNotFound();
//        }

        String ip = "";

        String finalLink = "http://" + ip + ":8080/reset-password/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        //prepare subject
        String subject = " Reset password ";

        emailService.send(email, subject, html);

    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));

    }
}
