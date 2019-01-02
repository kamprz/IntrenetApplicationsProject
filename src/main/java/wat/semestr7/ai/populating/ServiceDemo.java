package wat.semestr7.ai.populating;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.*;
import wat.semestr7.ai.services.dataservices.DiscountService;
import wat.semestr7.ai.utils.DateUtils;
import wat.semestr7.ai.entities.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ServiceDemo {

    private ConcertRepository concertRepository;
    private ConcertRoomRepository concertRoomRepository;
    private TicketRepository ticketRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;
    private DiscountService discountService;

    public ServiceDemo(ConcertRepository concertRepository, ConcertRoomRepository concertRoomRepository, TicketRepository ticketRepository,
                       SeatRepository seatRepository, UserRepository userRepository, DiscountService discountService) {
        this.concertRepository = concertRepository;
        this.concertRoomRepository = concertRoomRepository;
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.discountService = discountService;
    }

    public Concert testAddingConcert()
    {
        ConcertRoom concertRoom = getConcertRoom();
        Performers performers = getPerformers();
        List<PieceOfMusic> repertoire = getRepertoire();
        Concert concert = getConcert(concertRoom,performers,repertoire);
        return concertRepository.save(concert);
    }

    public void addUser()
    {
        AppUser admin = new AppUser();
        admin.setEmail("admin@filharmonia.pl");
        admin.setPassword("$2a$10$THdNsoLJC5UVWxItqXUbh.Ewf1qf6AGSIdmUb04A1K3.0tJmuD9au");
        admin.setRole("ADMIN");
        userRepository.save(admin);

        AppUser approver = new AppUser();
        approver.setEmail("approver@filharmonia.pl");
        approver.setPassword("$2a$10$THdNsoLJC5UVWxItqXUbh.Ewf1qf6AGSIdmUb04A1K3.0tJmuD9au");
        approver.setRole("APPROVER");
        userRepository.save(approver);
    }

    private List<PieceOfMusic> getRepertoire() {
        List<PieceOfMusic> list = new LinkedList<>();
        PieceOfMusic p1 = new PieceOfMusic();
        p1.setComposer("J.S.Bach");
        p1.setTitlePiece("Toccata and fugue d-minor");
        list.add(p1);
        PieceOfMusic p2 = new PieceOfMusic();
        p2.setTitlePiece("Symphonie IX");
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
        concert.setConcertTitle("Symfoniczny Koncert Niepodległościowy");
        try {concert.setDate(DateUtils.parseDate("11/11/2019 19:00"));
        } catch (ParseException e) { e.printStackTrace();}
        concert.setRepertoire(repertoire);
        concert.setConcertPerformers(performers);
        concert.setConcertRoom(concertRoom);
        concert.setTicketCost(new BigDecimal("149.99"));
        return concert;

    }

    private ConcertRoom getConcertRoom()
    {
        ConcertRoom concertRoom = new ConcertRoom();
        concertRoom.setConcertRoomName("Sala koncertowa Filharmonii Narodowej");
        concertRoom.setRentCosts(new BigDecimal("2000.00"));
        concertRoom.setAddress("Warszawa ul. Jasna 5");
        List<Seat> seats = new LinkedList<>();
        for(int i=1;i<11;i++) for(int j=1;j<11;j++)
        {
            Seat s = new Seat();
            s.setConcertRoom(concertRoom);
            s.setRow(i);
            s.setPosition(j);
            seats.add(s);
        }
        concertRoom.setSeats(seats);
        return concertRoom;
    }

    private void addDiscount()
    {
        Discount discount = new Discount();
        discount.setName("Studencki");
        discount.setPercents(50);
        discountService.addDiscount(discount);
    }

    private void addTicketsOldOne(Concert concert)
    {
        List<Seat> seats = concert.getConcertRoom().getSeats();

        for(int i=1;i<11;i+=2)
        {
            for(int j=1;j<11;j+=2)
            {
                Ticket t = new Ticket();
                t.setConcert(concert);
                t.setSeat(seats.get(i*10+j));
                ticketRepository.save(t);
            }
        }
    }

    public Concert populate()
    {
        Concert concert = testAddingConcert();
        addDiscount();
        return concert;
    }
}
