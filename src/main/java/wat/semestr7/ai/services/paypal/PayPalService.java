package wat.semestr7.ai.services.paypal;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.PurchaseDto;
import wat.semestr7.ai.dtos.TicketDto;
import wat.semestr7.ai.entities.*;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.dataservices.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayPalService
{
    private String clientId = "AZlsPDMXn18h5UYx4wcNvllVOZykMxQS2klA8gaxD7D6F99Z7FshHG1UsyRJwX5BtuoTkSmPYSWYrbVu";
    private String clientSecret = "ECNslREpbmRdaFiMYq0ZCr051CzzPxTmmIUi2aT_F8_hs-GnqaIQ1Z0tuOVUU4MfhEIBpV9NDUN1vNNw";
    private ConcertService concertService;
    private DiscountService discountService;
    private SeatService seatService;
    private TicketService ticketService;
    private PurchaseService purchaseService;

    public PayPalService(ConcertService concertService, DiscountService discountService, SeatService seatService,
                         TicketService ticketService, PurchaseService purchaseService) {
        this.concertService = concertService;
        this.discountService = discountService;
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.purchaseService = purchaseService;
    }

    public String createPayment(PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException {
        String sum = getAmountToPay(purchaseDto);
        System.out.println("SUM = " + sum);

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
        redirectUrls.setCancelUrl("http://localhost:8081/cancel");
        redirectUrls.setReturnUrl("http://localhost:8081/paypal/payment/complete");
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
            return redirectUrl;
        }
        return null;
    }

    public String completePayment(HttpServletRequest request) throws PayPalRESTException
    {
        Payment payment = new Payment();
        payment.setId(request.getParameter("paymentId"));

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getParameter("PayerID"));

        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
        Payment createdPayment = payment.execute(context, paymentExecution);
        if(createdPayment!=null)
        {
            Purchase purchase = purchaseService.getPurchaseByToken(request.getParameter("token"));
            purchaseService.setPurchasePaid(purchase);
            String mailToSendTickets = purchase.getEmail();
            //send tickets on email
            return mailToSendTickets;
        }
        return "Completing paypal payment unsuccesfull";
    }

    private void createTickets(PurchaseDto purchaseDto, Purchase purchase) throws EntityNotFoundException {
        Concert concert = concertService.getConcert(purchaseDto.getConcertId());
        for(TicketDto ticketDto : purchaseDto.getTickets())
        {
            ticketService.buyTicket(ticketDto,concert,purchase);
        }
    }

    private String getAmountToPay(PurchaseDto purchaseDto)
    {
        BigDecimal sum = new BigDecimal("0.00");
        sum.setScale(2,BigDecimal.ROUND_DOWN);
        BigDecimal standardTicketPrice = concertService.getConcertTicketPrice(purchaseDto.getConcertId());
        for(TicketDto ticket : purchaseDto.getTickets())
        {
            int percentsOfDiscount = discountService.getDiscountPercentsByName(ticket.getDiscountName());
            BigDecimal discountAmount = new BigDecimal(standardTicketPrice.doubleValue() * percentsOfDiscount / 100);
            BigDecimal actualPrice = standardTicketPrice.subtract(discountAmount);
            actualPrice.setScale(2,BigDecimal.ROUND_DOWN);
            sum = sum.add(actualPrice);
        }
        return sum.setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    private Purchase createPurchase(PurchaseDto purchaseDto, String token)
    {
        Purchase purchase = new Purchase();
        purchase.setPaypalID(token);
        purchase.setEmail(purchaseDto.getEmail());
        return purchase;
    }

    private String getToken(String redirectUrl)
    {
        return redirectUrl.substring(redirectUrl.indexOf("token=") + "token=".length());
    }
}
