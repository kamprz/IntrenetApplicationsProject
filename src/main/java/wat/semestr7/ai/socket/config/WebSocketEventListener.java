package wat.semestr7.ai.socket.config;

import com.example.chat.filh.ws.SocketService;
import com.example.chat.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wat.semestr7.ai.socket.service.SocketService;
import wat.semestr7.ai.socket.service.model.Message;

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

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        socketService.disconnect(1,username);

        if( username != null) {
            logger.info("User disconnected: " + username);

            Message message = new Message();
            message.setType(Message.MessageType.LEAVE);
            message.setSender(username);
            System.out.println("I am sending a message");
            System.out.println("subscribe address: " + subscribeAddress);
            int concertId = socketService.getConcertToWhichUserWasConnected(username);
            messagingTemplate.convertAndSend(subscribeAddress+"/"+concertId, message);
        }
    }
}
