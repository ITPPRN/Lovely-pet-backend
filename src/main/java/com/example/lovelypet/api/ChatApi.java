//    package com.example.lovelypet.api;
//
//    import com.example.lovelypet.business.ChatBusiness;
//    import com.example.lovelypet.model.ChatMessageRequest;
//    import org.springframework.http.HttpStatus;
//    import org.springframework.http.ResponseEntity;
//    import org.springframework.web.bind.annotation.PostMapping;
//    import org.springframework.web.bind.annotation.RequestBody;
//    import org.springframework.web.bind.annotation.RequestMapping;
//    import org.springframework.web.bind.annotation.RestController;
//
//    @RestController
//    @RequestMapping("/chat")
//    public class ChatApi {
//
//        private final ChatBusiness chatBusiness;
//
//        public ChatApi(ChatBusiness chatBusiness) {
//            this.chatBusiness = chatBusiness;
//        }
//
//        @PostMapping("/message")
//        public ResponseEntity<Void> post(@RequestBody ChatMessageRequest chatMessageRequest){
//            chatBusiness.post(chatMessageRequest);
//            return ResponseEntity.status(HttpStatus.OK).build();
//
//        }
//    }
