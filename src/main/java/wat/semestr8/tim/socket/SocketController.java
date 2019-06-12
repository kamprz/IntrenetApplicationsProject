package wat.semestr8.tim.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import wat.semestr8.tim.dtos.SocketMessage;

import javax.validation.Valid;

@Controller
public class SocketController {

    private final SocketService socketService;

    public SocketController(SocketService socketService) {
        this.socketService = socketService;
    }

    @MessageMapping("/socket.sendMessage")
    public void receiveMessage(@Valid @Payload SocketMessage message) {
        socketService.seatOccupationChanged(message);

    }

    /*@MessageMapping("/socket.addUser")
    //@SendTo("/topic/public")
    public Message2 addUser(@Valid @Payload Message2 message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        messagingTemplate.convertAndSend(subscribeAddress,message);
        return message;
    }*/
}
