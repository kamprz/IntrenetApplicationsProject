package wat.semestr8.tim.socket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wat.semestr8.tim.socket.service.SocketService;
import wat.semestr8.tim.socket.service.model.Message2;
import wat.semestr8.tim.socket.service.model.SocketMessage;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Value("${socket.subscribeAddress}")
    private String subscribeAddress;
    private final SimpMessageSendingOperations messagingTemplate;
    private final SocketService socketService;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, SocketService socketService) {
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
        SocketMessage.MessageType type = SocketMessage.MessageType.valueOf(typeStr);


        if( androidId != null && typeStr != null) {
            socketService.disconnect(androidId,type);
            messagingTemplate.convertAndSend(subscribeAddress+"/"+concertId, message);
        }
    }
}
