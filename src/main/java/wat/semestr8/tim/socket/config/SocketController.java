package wat.semestr8.tim.socket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import wat.semestr8.tim.socket.service.SocketService;
import wat.semestr8.tim.dtos.SocketMessage;

import javax.validation.Valid;

@Controller
public class SocketController {

    private final SocketService socketService;
    private final SimpMessageSendingOperations messagingTemplate;
    @Value("${socket.subscribeAddress}")
    private String subscribeAddress;

    public SocketController(SocketService socketService, SimpMessageSendingOperations messagingTemplate) {
        this.socketService = socketService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/socket.sendMessage")
    public SocketMessage receiveMessage(@Valid @Payload SocketMessage message, SimpMessageHeaderAccessor headerAccessor) {
        SocketMessage s = socketService.seatOccupationChanged(message);

        messagingTemplate.convertAndSend(subscribeAddress,message);
        return message;
    }

    /*@MessageMapping("/socket.addUser")
    //@SendTo("/topic/public")
    public Message2 addUser(@Valid @Payload Message2 message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        messagingTemplate.convertAndSend(subscribeAddress,message);
        return message;
    }*/
}
