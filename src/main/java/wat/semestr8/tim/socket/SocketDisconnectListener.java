package wat.semestr8.tim.socket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wat.semestr8.tim.dtos.SocketMessage;

@Component
public class SocketDisconnectListener {

    private final SocketService socketService;

    public SocketDisconnectListener(SocketService socketService) {
        this.socketService = socketService;
    }

    //Napisać obsługę wiadomości disconnect i forward
    @EventListener
    public void handleWebSocketDisconnect (SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) headerAccessor.getSessionAttributes().get("username");

        if(userId != null){
            socketService.userSuddenlyDisconnected(userId);
        }
    }
}
