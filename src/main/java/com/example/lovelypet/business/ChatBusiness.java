//package com.example.lovelypet.business;
//
//import com.example.lovelypet.model.ChatMessage;
//import com.example.lovelypet.model.ChatMessageRequest;
//import com.example.lovelypet.util.SecurityUtil;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//import java.util.Optional;
//
//public class ChatBusiness {
//
//    private final SimpMessagingTemplate template;
//
//    public ChatBusiness(SimpMessagingTemplate template) {
//        this.template = template;
//    }
//
//    public void post(ChatMessageRequest request) {
//        Optional<String> opt = SecurityUtil.getCurrentUserId();
//
//        if (opt.isEmpty()){
//            return;
//        }
//
//        // TODO: validate
//
//        final String destination = "chat";
//        ChatMessage payload = new ChatMessage();
//        payload.setFrom(opt.get());
//        payload.setMessage(request.getMessage());
//        template.convertAndSend(destination);
//    }
//}
