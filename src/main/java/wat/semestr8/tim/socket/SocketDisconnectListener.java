package wat.semestr8.tim.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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

    @EventListener
    public void handleWebSocketDisconnect (SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String androidId = (String) headerAccessor.getSessionAttributes().get("id");
        String typeStr = (String) headerAccessor.getSessionAttributes().get("type");
        Integer concertId = Integer.parseInt((String)headerAccessor.getSessionAttributes().get("concertId"));
        SocketMessage.MessageType type = SocketMessage.MessageType.valueOf(typeStr);

        if( androidId != null && typeStr != null && concertId != null) {
            socketService.disconnect(androidId, type, concertId);
        }
    }
}
