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

    @MessageMapping("/socket.addUser")
    public void addUser(SimpMessageHeaderAccessor headerAccessor, @Payload SocketMessage message) {
        headerAccessor.getSessionAttributes().put("username", message.getAndroidId());
    }
}
