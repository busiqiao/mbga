package cn.waitfor.mbga.controller;

import static cn.waitfor.mbga.config.WebSocketConfig.*;

import cn.waitfor.mbga.dto.ws.InboundMessage;
import cn.waitfor.mbga.dto.ws.OutboundMessage;
import jakarta.annotation.Resource;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketStompController {

  @Resource private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/entry")
  @SendTo(TOPIC)
  public OutboundMessage enterChatRoom(InboundMessage inboundMessage) {
    return new OutboundMessage(String.format("Greetings %s", inboundMessage.getName()));
  }

  @MessageMapping("/chat")
  public void handlePrivateChat(InboundMessage msg, Principal user) {
    simpMessagingTemplate.convertAndSendToUser(
        user.getName(), QUEUE, String.format("Hi, %s", msg.getName()));
  }
}

