package wat.semestr8.tim.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.dtos.SocketMessage;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.model.SeatOccupied;
import wat.semestr8.tim.socket.SocketService;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class SocketUnitTest {

    private SocketService service;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    @Before
    public void init(){
        service = new SocketService(null);
    }

    @Test
    public void socketKeepsInfoAboutSeatsOccupiedAfterGettingMessage()
    {
        //given
        SocketMessage message = new SocketMessage();
        String userId = "123456789";
        Integer concertId = 1;
        message.setAndroidId(userId);
        message.setConcertId(concertId);
        message.setType(SocketMessage.MessageType.LOCKED);
        SeatDto seat1 = new SeatDto(1,1);
        SeatDto seat2 = new SeatDto(1,2);
        message.setSeat(Arrays.asList(seat1));
        //when
        service.seatOccupationChanged(message);
        //then
        assertTrue(service.getConcertIdByUserId(userId).contains(concertId));

        assertTrue(service.getSeatsOccupiedByConcertId(concertId).contains(mapper.seatDtoToSeatOccupied(seat1)));
        assertFalse(service.getSeatsOccupiedByConcertId(concertId).contains(mapper.seatDtoToSeatOccupied(seat2)));

        assertTrue(service.getSeatsOccupiedByConcertIdAndUserId(concertId,userId).contains(mapper.seatDtoToSeatOccupied(seat1)));
        assertFalse(service.getSeatsOccupiedByConcertIdAndUserId(concertId,userId).contains(mapper.seatDtoToSeatOccupied(seat2)));
    }

    @Test
    public void secondMessageForSamePlaceWillReturnNull()
    {
        //given
        SocketMessage message = new SocketMessage();
        String userId = "123456789";
        Integer concertId = 1;
        message.setAndroidId(userId);
        message.setConcertId(concertId);
        message.setType(SocketMessage.MessageType.LOCKED);
        message.setSeat(Arrays.asList(
                new SeatDto(1,1),
                new SeatDto(1,2),
                new SeatDto(1,3))
        );
        //when
        service.seatOccupationChanged(message);
        SocketMessage mess = service.seatOccupationChanged(message);
        //then
        assertNull(null,mess);
    }

    @Test(expected = NullPointerException.class)
    public void lastUsersSeatByConcertIdUnlockedShouldRemoveUserFromSocketMaps()
    {
        //given
        SocketMessage message = new SocketMessage();
        String userId = "123456789";
        Integer concertId = 1;
        message.setAndroidId(userId);
        message.setConcertId(concertId);
        message.setType(SocketMessage.MessageType.LOCKED);
        message.setSeat(Arrays.asList(new SeatDto(1,1)));
        //new SeatDto(1,2)
        //when
        service.seatOccupationChanged(message);
        //mes
        message.setType(SocketMessage.MessageType.UNLOCKED);
        service.seatOccupationChanged(message);

        //then
        assertNull(service.getConcertIdByUserId(userId));
        assertNull(service.getSeatsOccupiedByConcertId(concertId));
        service.getSeatsOccupiedByConcertIdAndUserId(concertId,userId).isEmpty();
    }

    @Test
    public void XlastUsersSeatByConcertIdUnlockedShouldRemoveUserFromSocketMaps()
    {
        //given
        SocketMessage message = new SocketMessage();
        String userId = "123456789";
        Integer concertId = 1;
        message.setAndroidId(userId);
        message.setConcertId(concertId);
        message.setType(SocketMessage.MessageType.LOCKED);
        SeatDto seat = new SeatDto(1,1);
        message.setSeat(Arrays.asList(seat));
        //when
        service.seatOccupationChanged(message);
        message.setSeat(Arrays.asList(new SeatDto(1,2)));
        service.seatOccupationChanged(message);
        message.setType(SocketMessage.MessageType.UNLOCKED);
        service.seatOccupationChanged(message);

        //then
        assertTrue(service.getConcertIdByUserId(userId).contains(concertId));
        assertTrue(service.getSeatsOccupiedByConcertId(concertId).contains(mapper.seatDtoToSeatOccupied(seat)));
        assertTrue(service.getSeatsOccupiedByConcertIdAndUserId(concertId,userId).contains(mapper.seatDtoToSeatOccupied(seat)));;
    }
}

