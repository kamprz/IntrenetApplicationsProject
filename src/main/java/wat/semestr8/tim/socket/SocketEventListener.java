package wat.semestr8.tim.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wat.semestr8.tim.dtos.SocketMessage;

@Component
public class SocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SocketEventListener.class);
    @Value("${socket.subscribeAddress}")
    private String subscribeAddress;
    private final SimpMessageSendingOperations messagingTemplate;
    private final SocketService socketService;

    public SocketEventListener(SimpMessageSendingOperations messagingTemplate, SocketService socketService) {
        this.messagingTemplate = messagingTemplate;
        this.socketService = socketService;
    }

    @EventListener
    public void handleWebSocketConnect (SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnect (SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String androidId = (String) headerAccessor.getSessionAttributes().get("id");
        String typeStr = (String) headerAccessor.getSessionAttributes().get("type");
        Integer concertId = Integer.parseInt((String)headerAccessor.getSessionAttributes().get("concertId"));
        SocketMessage.MessageType type = SocketMessage.MessageType.valueOf(typeStr);


        if( androidId != null && typeStr != null && concertId != null) {
            SocketMessage unlocked = socketService.disconnect(androidId, type, concertId);
            if( unlocked != null){
                messagingTemplate.convertAndSend(subscribeAddress+"/"+concertId, unlocked);
            }
        }
    }
}
