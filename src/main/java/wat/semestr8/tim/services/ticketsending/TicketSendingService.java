package wat.semestr8.tim.services.ticketsending;

import org.springframework.stereotype.Service;
import wat.semestr8.tim.entities.Concert;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Ticket;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.services.dataservices.ConcertService;
import wat.semestr8.tim.services.dataservices.PurchaseService;
import wat.semestr8.tim.services.dataservices.TicketService;
import wat.semestr8.tim.services.ticketsending.email.EmailClient;
import wat.semestr8.tim.services.ticketsending.ticketgenerator.MapGenerator;
import wat.semestr8.tim.services.ticketsending.ticketgenerator.TicketGenerator;
import wat.semestr8.tim.utils.DateUtils;
import wat.semestr8.tim.utils.PriceUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TicketSendingService
{
    private ConcertService concertService;
    private TicketService ticketService;
    private PurchaseService purchaseService;

    public TicketSendingService(ConcertService concertService, TicketService ticketService, PurchaseService purchaseService) {
        this.concertService = concertService;
        this.ticketService = ticketService;
        this.purchaseService = purchaseService;
    }

    public boolean sendTickets(int purchaseId) throws IOException, MessagingException, EntityNotFoundException {
        Purchase purchase = purchaseService.getPurchaseByPurchaseId(purchaseId);

        List<Map> tickets = new LinkedList<>();
        Concert concert = concertService.getConcertByPurchase(purchase);

        for(Ticket ticket : ticketService.getAllTicketsByPurchase(purchase))
        {
            Map map = new MapGenerator()
                    .putConcertTitle(concert.getConcertTitle())
                    .putDate(DateUtils.formatDate(concert.getDate()))
                    .putPosition(ticket.getSeat().getPosition()+"")
                    .putRow(ticket.getSeat().getRow()+"")
                    .putPrice(PriceUtils.getTicketPrice(
                            concert.getTicketCost(),
                            ticket.getDiscount().getPercents())
                            .setScale(2, BigDecimal.ROUND_UP).toString())
                    .putDiscount(ticket.getDiscount().getPercents()+"%")
                    .get();
            tickets.add(map);
        }

        TicketGenerator ticketGenerator = new TicketGenerator(purchase);
        ticketGenerator.generateTickets(tickets);

        EmailClient emailClient = new EmailClient(purchase.getEmail());
        emailClient.send();
        return true;
    }
}
