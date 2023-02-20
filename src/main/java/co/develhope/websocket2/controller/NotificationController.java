package co.develhope.websocket2.controller;

import co.develhope.websocket2.dto.ClientMessageDTO;
import co.develhope.websocket2.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendMessageToSubscribers(@RequestBody MessageDTO message) {
        simpMessagingTemplate.convertAndSend("/topic/broadcast", message);
        return ResponseEntity.ok("Message sent to subscribers on /topic/broadcast");
    }

    @MessageMapping("client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO handleMessageFromWebSocket(@RequestBody ClientMessageDTO message) {
        return new MessageDTO(message.getClientName(), message.getClientAlert(), message.getClientMsg());
    }

}