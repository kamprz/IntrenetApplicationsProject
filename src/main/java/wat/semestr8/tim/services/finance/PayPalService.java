package wat.semestr8.tim.services.finance;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.AndroidTicketDto;
import wat.semestr8.tim.dtos.PurchaseDto;
import wat.semestr8.tim.dtos.RedirectURL;
import wat.semestr8.tim.dtos.TicketDto;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.entities.Concert;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.entities.Ticket;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.exceptions.customexceptions.PaymentTimeoutException;
import wat.semestr8.tim.services.dataservices.*;
import wat.semestr8.tim.services.ticketsending.TicketSendingService;
import wat.semestr8.tim.socket.SocketService;
import wat.semestr8.tim.utils.PriceUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayPalService
{
    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.clientSecret}")
    private String clientSecret;
    @Value("${paypal.returnUrl}")
    private String returnUrl;
    @Value("${paypal.cancelUrl}")
    private String cancelUrl;


    private ConcertService concertService;
    private DiscountService discountService;
    private TicketService ticketService;
    private PurchaseService purchaseService;
    private TicketSendingService ticketSendingService;
    private TransactionService transactionService;
    private SocketService socketService;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public PayPalService(ConcertService concertService, DiscountService discountService, TicketService ticketService,
                         PurchaseService purchaseService, TicketSendingService ticketSendingService, TransactionService transactionService, SocketService socketService) {
        this.concertService = concertService;
        this.discountService = discountService;
        this.ticketService = ticketService;
        this.purchaseService = purchaseService;
        this.ticketSendingService = ticketSendingService;
        this.transactionService = transactionService;
        this.socketService = socketService;
    }

    public RedirectURL createPayment(PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException {
        String sum = getAmountToPay(purchaseDto);
        Amount amount = new Amount();
        amount.setCurrency("PLN");
        amount.setTotal(sum);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;

        String redirectUrl = "";
        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
        createdPayment = payment.create(context);
        if(createdPayment!=null){
            List<Links> links = createdPayment.getLinks();
            for (Links link:links) {
                if(link.getRel().equals("approval_url")){
                    redirectUrl = link.getHref();
                    break;
                }
            }
            String token = getToken(redirectUrl);
            Purchase purchase = createPurchase(purchaseDto, token);
            createTickets(purchaseDto,purchase);
            return new RedirectURL(redirectUrl);
        }
        return null;
    }

    public List<AndroidTicketDto> completePayment(String paymentId, String PayerID, String token) throws PayPalRESTException, EntityNotFoundException, MessagingException, PaymentTimeoutException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerID);

        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
        Payment createdPayment = payment.execute(context, paymentExecution);
        if(createdPayment!=null)
        {
            Purchase purchase = purchaseService.getPurchaseByToken(token);
            purchaseService.setPurchasePaid(purchase);
            transactionService.addTransaction(purchase);
            try{
                ticketSendingService.sendTickets(purchase.getIdPurchase());
            }catch(IOException e){ throw new PayPalRESTException("Not valid attempt to pay for ticket"); }
            purchaseFinished(purchase);
            List<Ticket> allTicketsByPurchase = ticketService.getAllTicketsByPurchase(purchase);
            System.out.println("ALL TICKETS: " + allTicketsByPurchase);
            return allTicketsByPurchase.stream().map(mapper::ticketForAndroid).collect(Collectors.toList());
        }
        throw new PayPalRESTException("Completing paypal payment unsuccesfull");
    }

    private void createTickets(PurchaseDto purchaseDto, Purchase purchase) throws EntityNotFoundException {
        Concert concert = concertService.getConcert(purchaseDto.getConcertId());
        for(TicketDto ticketDto : purchaseDto.getTickets())
        {
            ticketService.buyTicket(ticketDto,concert,purchase);
        }
    }

    private String getAmountToPay(PurchaseDto purchaseDto) throws EntityNotFoundException {
        BigDecimal sum = new BigDecimal("0.00");
        sum.setScale(2,BigDecimal.ROUND_DOWN);
        BigDecimal standardTicketPrice = concertService.getConcertTicketPrice(purchaseDto.getConcertId());
        for(TicketDto ticket : purchaseDto.getTickets())
        {
            try{
                int percentsOfDiscount = discountService.getDiscountPercentsByName(ticket.getDiscountName());
                BigDecimal actualPrice = PriceUtils.getTicketPrice(standardTicketPrice,percentsOfDiscount);
                sum = sum.add(actualPrice);
            }catch(NullPointerException e){ throw new EntityNotFoundException("Nie ma takiej znizki."); }
        }
        return sum.setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    private Purchase createPurchase(PurchaseDto purchaseDto, String token)
    {
        Purchase purchase = new Purchase();
        purchase.setPaypalID(token);
        purchase.setTimestamp(new Date());
        purchase.setEmail(purchaseDto.getEmail());
        purchase.setUserId(purchaseDto.getUserId());
        return purchase;
    }

    private String getToken(String redirectUrl)
    {
        return redirectUrl.substring(redirectUrl.indexOf("token=") + "token=".length());
    }

    private void purchaseFinished(Purchase purchase) throws PayPalRESTException {
        List<Seat> seats;
        int idConcert;
        String userId;

        try{
            idConcert = purchase.getTickets().get(0).getConcert().getIdConcert();

        userId = purchase.getUserId();
        seats = purchase.getTickets().stream().map(ticket -> ticket.getSeat()).collect(Collectors.toList());

        socketService.purchaseFinished(seats,idConcert,userId);
        }catch(NullPointerException e) {
            throw new PayPalRESTException("Not valid attempt to pay for ticket");
        }
    }
}
