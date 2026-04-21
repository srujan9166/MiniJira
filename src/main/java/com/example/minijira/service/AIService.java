package com.example.minijira.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    
    
    public final ChatClient chatClient;
    public AIService(ChatClient.Builder chatClientBuilder) {
       
        this.chatClient = chatClientBuilder.build();
    }

    public String askAI(String prompt){
        return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
    }


}
