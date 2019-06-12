package wat.semestr8.tim.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.SocketMessage;

@Service
public class SocketBroadcaster {
    @Value("${socket.subscribeAddress}")
    private String subscribeAddress;
    private final SimpMessageSendingOperations messagingTemplate;

    public SocketBroadcaster(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(SocketMessage message){
        messagingTemplate.convertAndSend(subscribeAddress+"/"+message.getConcertId(), message);
    }
}
