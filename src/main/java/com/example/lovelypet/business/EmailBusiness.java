package com.example.lovelypet.business;

import com.example.common.EmailRequest;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.EmaillException;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class EmailBusiness {
    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException, MessagingException {

        //prepare content(HTML)
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmaillException.templateNotFound();
        }

        log.info("Token" + token);

        String finalLink = "http://localhost:8080/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject("Please activate your account");
        request.setContent(html);

        CompletableFuture<SendResult<String, EmailRequest>> completableFuture = kafkaTemplate.send("activation-email", request);

        ListenableFuture<SendResult<String, EmailRequest>> future = new CompletableToListenableFutureAdapter<>(completableFuture);

        future.addCallback(new ListenableFutureCallback<SendResult<String, EmailRequest>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sent fail");
                log.error(ex);
            }

            @Override
            public void onSuccess(SendResult<String, EmailRequest> result) {
                log.info("kafka sent success");
                log.info(result);
            }
        });
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));

    }
}
