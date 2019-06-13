package wat.semestr8.tim.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.dtos.SocketMessage;
import wat.semestr8.tim.populating.ServiceDemo;
import wat.semestr8.tim.services.dataservices.SeatService;
import wat.semestr8.tim.socket.SocketService;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreeSeatsTest {
    @Autowired
    private SeatService seatService;
    @Autowired
    private ServiceDemo populatingService;
    @Autowired
    private SocketService socketService;

    @Before
    public void populate(){
        populatingService.populate();

    }

    @Test
    public void test(){
        //populate creates concert with id=1 and no tickets sent
        SocketMessage message = new SocketMessage();
        message.setConcertId(1);
        message.setAndroidId("12321412asd");

        SeatDto seat1 = new SeatDto(1,1);
        message.setSeat(Arrays.asList(seat1));
        message.setType(SocketMessage.MessageType.LOCKED);
        socketService.seatOccupationChanged(message);

        SeatDto seat2 = new SeatDto(1,2);
        message.setSeat(Arrays.asList(seat2));
        socketService.seatOccupationChanged(message);

        message.setType(SocketMessage.MessageType.UNLOCKED);
        socketService.seatOccupationChanged(message);

        List<SeatDto> freeSeats = seatService.getFreeSeatsOnConcert(1);
        System.out.println(socketService.getSeatsOccupiedByConcertId(1));
        System.out.println(freeSeats);
        Assert.assertTrue(freeSeats.contains(seat2));
        Assert.assertFalse(freeSeats.contains(seat1));
    }
}
