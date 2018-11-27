package wat.semestr7.ai.services;

import org.hibernate.Hibernate;
import org.hibernate.context.spi.CurrentSessionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wat.semestr7.ai.repositories.*;
import wat.semestr7.ai.utils.DateUtils;
import wat.semestr7.ai.entities.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceDemo {

    private ConcertRepository concertRepository;
    private ConcertRoomRepository concertRoomRepository;
    private PerformersRepository performersRepository;
    private PieceOfMusicRepository pieceOfMusicRepository;
    private SeatRepository seatRepository;
    private TicketRepository ticketRepository;

    public ServiceDemo(ConcertRepository concertRepository,
                       ConcertRoomRepository concertRoomRepository,
                       PerformersRepository performersRepository,
                       PieceOfMusicRepository pieceOfMusicRepository,
                       SeatRepository seatRepository,
                       TicketRepository ticketRepository)
    {
        this.concertRepository = concertRepository;
        this.concertRoomRepository = concertRoomRepository;
        this.performersRepository = performersRepository;
        this.pieceOfMusicRepository = pieceOfMusicRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    //@PostConstruct
    private void testAddingConcert()
    {
        ConcertRoom concertRoom = getConcertRoom();
        Performers performers = getPerformers();
        List<PieceOfMusic> repertoire = getRepertoire();
        Concert concert = getConcert(concertRoom,performers,repertoire);
        concertRepository.save(concert);
    }

    @Transactional()
    public List<String> testConcertRoom()
    {

        ConcertRoom concertRoom = new ConcertRoom();
        concertRoom.setConcertRoomName("Sala koncertowa");
        concertRoom.setRentCosts(new BigDecimal("2000.00"));
        List<Seat> seats = new LinkedList<>();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++)
        {
            Seat s = new Seat();
            s.setFloor(0);
            s.setConcertRoom(concertRoom);
            s.setRow(i);
            s.setPosition(j);
            seats.add(s);
        }
        concertRoom.setSeats(seats);
        concertRoomRepository.save(concertRoom);
        ConcertRoom cr = concertRoomRepository.findById(1).get();
        System.out.println(cr);
        //throw new RuntimeException();
        Hibernate.initialize(cr.getSeats());

        return cr.getSeats().stream().map(s->s.toString()).collect(Collectors.toList());
    }

    private List<PieceOfMusic> getRepertoire() {
        List<PieceOfMusic> list = new LinkedList<>();
        PieceOfMusic p1 = new PieceOfMusic();
        p1.setComposer("J.S.Bach");
        p1.setDurationInMinutes(10);
        p1.setTitlePiece("Toccata and fugue d-minor");
        list.add(p1);
        PieceOfMusic p2 = new PieceOfMusic();
        p2.setTitlePiece("Symphonie IX");
        p2.setDurationInMinutes(75);
        p2.setComposer("Ludwig van Beethoven");
        list.add(p2);
        return list;
    }

    private Performers getPerformers() {
        Performers performers = new Performers();
        performers.setCostOfPersonnel(new BigDecimal("4000.00"));
        performers.setDetails("Pełna orkiestra symfoniczna");
        return performers;
    }

    private Concert getConcert(ConcertRoom concertRoom,Performers performers, List<PieceOfMusic> repertoire)
    {
        Concert concert = new Concert();
        concert.setAdditionalOrganisationCosts(new BigDecimal("1000.00"));
        concert.setConcertTitle("Koncert Niepodległościowy");
        try {concert.setDate(DateUtils.parseDate("11/11/2018"));
        } catch (ParseException e) { e.printStackTrace();}
        concert.setRepertoire(repertoire);
        concert.setConcertPerformers(performers);
        concert.setConcertRoom(concertRoom);
        return concert;

    }

    private ConcertRoom getConcertRoom()
    {
        ConcertRoom concertRoom = new ConcertRoom();
        concertRoom.setConcertRoomName("Sala koncertowa");
        concertRoom.setRentCosts(new BigDecimal("2000.00"));
        return concertRoom;
    }

}
