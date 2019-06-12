package wat.semestr8.tim.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wat.semestr8.tim.dtos.PurchaseDto;
import wat.semestr8.tim.dtos.TicketDto;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.populating.ServiceDemo;
import wat.semestr8.tim.services.finance.PayPalService;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayPalFunctionalTest {

    private PayPalService payPalService;
    private ServiceDemo populate;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        populate.populate();
    }

    @Test
    public void givenConcertAndPurchaseForPayPalExecutesPayment() throws PayPalRESTException, EntityNotFoundException {
        Integer concertId = 1;
        boolean isPaid = false;
        String email = "some@email.com";
        String userId = "myID";
        Integer seatRow = 5;
        Integer seatCol = 5;
        String discountName = "Normalny";
        TicketDto ticket1 = new TicketDto(seatRow,seatCol,discountName);
        TicketDto ticket2 = new TicketDto(seatRow,++seatCol,discountName);
        List<TicketDto> tickets = Arrays.asList(ticket1,ticket2);

        PurchaseDto purchaseDto = new PurchaseDto(concertId,isPaid,email,tickets,userId);

        payPalService.createPayment(purchaseDto);
    }
}
