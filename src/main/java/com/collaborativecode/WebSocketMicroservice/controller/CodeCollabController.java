package com.collaborativecode.WebSocketMicroservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@Controller
@CrossOrigin
public class CodeCollabController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Handle code updates
    @MessageMapping("/codeUpdate/{roomId}")
    public void handleCodeUpdate(@DestinationVariable String roomId, @Payload Map<String, String> payload) {
        String code = payload.get("code");
        // Broadcast code update to all subscribers in the room
        messagingTemplate.convertAndSend("/topic/room/" + roomId,
                Map.of("event", "codeUpdate", "code", code));
    }

    // Handle room joining
    @MessageMapping("/joinRoom/{roomId}")
    public void handleJoinRoom(@DestinationVariable String roomId, @Payload Map<String, String> payload) {
        String username = payload.get("username");
        // Notify all subscribers in the room about the new user
        messagingTemplate.convertAndSend("/topic/room/" + roomId,
                Map.of("event", "userJoined", "message", username + " has joined the room"));
    }

}