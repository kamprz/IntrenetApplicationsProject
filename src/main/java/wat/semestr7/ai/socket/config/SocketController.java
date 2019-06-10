package wat.semestr7.ai.socket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import wat.semestr7.ai.socket.service.SocketService;
import wat.semestr7.ai.socket.service.model.Message;

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

    //musze dostac idkoncertu
    //"10_9_t_123456789"
    @MessageMapping("/socket.sendMessage")
    public Message send (@Valid @Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("I am getting a message : " + message.getContent());

        String s = socketService.seatOccupationChanged(1, message.getContent());
        System.out.println("controller resulted in " + s);
        messagingTemplate.convertAndSend(subscribeAddress,message);
        return message;
    }

    @MessageMapping("/socket.addUser")
    //@SendTo("/topic/public")
    public Message addUser(@Valid @Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        System.out.println("HEADER: \n " + headerAccessor);
        messagingTemplate.convertAndSend(subscribeAddress,message);
        return message;
    }
}
