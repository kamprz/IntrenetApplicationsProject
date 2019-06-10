package wat.semestr8.tim.populating;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.ConcertDto;
import wat.semestr8.tim.dtos.PerformersDto;
import wat.semestr8.tim.dtos.PieceOfMusicDto;
import wat.semestr8.tim.dtos.mappers.ConcertMapper;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.AuthorityRepository;
import wat.semestr8.tim.repositories.RoleRepository;
import wat.semestr8.tim.security.SecurityAuthorities;
import wat.semestr8.tim.security.user.AppUser;
import wat.semestr8.tim.security.user.Authority;
import wat.semestr8.tim.security.user.Role;
import wat.semestr8.tim.services.dataservices.ConcertService;
import wat.semestr8.tim.services.dataservices.DiscountService;
import wat.semestr8.tim.services.dataservices.PerformersService;
import wat.semestr8.tim.services.dataservices.PieceOfMusicService;
import wat.semestr8.tim.entities.*;
import wat.semestr8.tim.repositories.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class ServiceDemo {
    private ConcertService concertService;
    private ConcertRepository concertRepository;
    private ConcertRoomRepository concertRoomRepository;
    private TicketRepository ticketRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;
    private DiscountService discountService;
    private RoleRepository roleRepository;
    private AuthorityRepository authorityRepository;
    private PerformersService performersService;
    private PieceOfMusicService pieceOfMusicService;
    private PurchaseRepository purchaseRepository;
    private TransactionRepository transactionRepository;
    private ConcertMapper concertMapper;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    private static int dateAdd = 0;
    private static BigDecimal amountBefore = new BigDecimal("0.0");

    public ServiceDemo(ConcertRepository concertRepository, ConcertRoomRepository concertRoomRepository, TicketRepository ticketRepository,
                       SeatRepository seatRepository, UserRepository userRepository, DiscountService discountService, RoleRepository roleRepository,
                       AuthorityRepository authorityRepository, PerformersService performersService, PurchaseRepository purchaseRepository,
                       ConcertMapper concertMapper, TransactionRepository transactionRepository) {
        this.concertRepository = concertRepository;
        this.concertRoomRepository = concertRoomRepository;
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.discountService = discountService;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.performersService = performersService;
        this.concertMapper = concertMapper;
        this.purchaseRepository = purchaseRepository;
        this.transactionRepository = transactionRepository;

    }

    private final Calendar calendar = Calendar.getInstance();
    private final String ORKIESTRA_SYMFONICZNA = "Pełna orkiestra symfoniczna";
    private final String ORKIESTRA_SMYCZKOWA_Z_CHOREM_I_ORGANAMI = "Orkiestra smyczkowa, organy i chór";
    private List<PieceOfMusicDto> repertoireSymph = new LinkedList<>();
    private List<PieceOfMusicDto> repertoireCarols = new LinkedList<>();

    public void testAddingConcert(String date, String title, String ticketPrice, String performers, List<PieceOfMusicDto> repertoire)  {
        ConcertDto concertDto = new ConcertDto();
        concertDto.setApproved(true);
        concertDto.setConcertPerformers(performers);
        concertDto.setTicketCost(new BigDecimal(ticketPrice));
        concertDto.setAdditionalOrganisationCosts(new BigDecimal("1000.00"));
        concertDto.setDate(date);

        concertDto.setConcertTitle(title);
        concertDto.setRepertoire(repertoire);
        try {
            Concert concert = concertRepository.save(concertMapper.dtoToConcert(concertDto));
            calendar.setTime(concert.getDate());
            addSoldTickets(concert);
            BigDecimal concertCost = concert.getAdditionalOrganisationCosts().add(concert.getConcertRoom().getRentCosts()).add(concert.getConcertPerformers().getCostOfPersonnel());
            Transaction transaction = new Transaction();
            transaction.setTransactionDetails("Koncert : " + concert.getConcertTitle() + ". Data : " + concert.getDate());
            transaction.setTitleTransaction("Opłaty za organizację koncertu");
            calendar.setTime(concert.getDate());
            calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + 10);
            transaction.setDate(calendar.getTime());
            transaction.setTransactionSum(concertCost.multiply(new BigDecimal("-1")));
            transaction.setAmountAfterTransaction(amountBefore.add(transaction.getTransactionSum()));
            amountBefore = transaction.getAmountAfterTransaction();
            transactionRepository.save(transaction);
        }
        catch (EntityNotFoundException e) { e.printStackTrace(); }
    }

    private void addSoldTickets(Concert concert) throws EntityNotFoundException {
        calendar.setTime(concert.getDate());
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-10);
        for(int p = 2; p<9; p++)
        {
            Random random = new Random();
            Purchase purchase = new Purchase();
            purchase.setPaid(true);
            purchase.setEmail("email@dot.com");
            purchase.setPaypalID("123aldsalskd" + random.nextInt(123456));
            purchase.setTimestamp(new Date());
            List<Ticket> tickets = new LinkedList<>();
            for (int i = 2; i < 9; i++)
            {
                Ticket ticket = new Ticket();
                ticket.setDiscount(discountService.getByName("Normalny"));
                ticket.setConcert(concert);
                ticket.setSeat(seatRepository.findFirstByRowAndPosition(p,i));
                ticket.setPurchase(purchase);
                tickets.add(ticket);
            }
            purchase.setTickets(tickets);
            purchaseRepository.save(purchase);
            Transaction transaction = new Transaction();
            transaction.setTransactionDetails("Koncert : " + concert.getConcertTitle() + ". Data : " + concert.getDate());
            transaction.setTitleTransaction("Zakup biletów na koncert");
            calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + 30);
            transaction.setDate(calendar.getTime());

            transaction.setTransactionSum(concert.getTicketCost().multiply(new BigDecimal("7")));
            transaction.setAmountAfterTransaction(amountBefore.add(transaction.getTransactionSum()));
            amountBefore = transaction.getAmountAfterTransaction();
            transactionRepository.save(transaction);
        }
    }

    @PostConstruct
    public void addUser()
    {
        Authority auth1 = new Authority(SecurityAuthorities.ADMIN);
        Authority auth2 = new Authority(SecurityAuthorities.APPROVE);
        Authority auth3 = new Authority(SecurityAuthorities.READ_NOT_APPROVED);
        Authority auth4 = new Authority(SecurityAuthorities.DELETE_NOT_APPROVED);
        authorityRepository.save(auth1);
        authorityRepository.save(auth2);
        authorityRepository.save(auth3);
        authorityRepository.save(auth4);

        List<Authority> adminAuth = new LinkedList<>();
        adminAuth.add(auth1);
        adminAuth.add(auth3);

        List<Authority> approverAuth = new LinkedList<>();
        approverAuth.add(auth2);
        approverAuth.add(auth3);
        approverAuth.add(auth4);

        Role adminRole = new Role("ADMIN",adminAuth);
        Role approverRole = new Role("APPROVER", approverAuth);

        roleRepository.save(adminRole);
        roleRepository.save(approverRole);

        AppUser admin = new AppUser();
        admin.setEmail("admin@filharmonia.pl");
        admin.setPassword("$2a$10$THdNsoLJC5UVWxItqXUbh.Ewf1qf6AGSIdmUb04A1K3.0tJmuD9au");
        admin.setRole(adminRole);
        userRepository.save(admin);

        AppUser approver = new AppUser();
        approver.setEmail("approver@filharmonia.pl");
        approver.setPassword("$2a$10$THdNsoLJC5UVWxItqXUbh.Ewf1qf6AGSIdmUb04A1K3.0tJmuD9au");
        approver.setRole(approverRole);
        userRepository.save(approver);
    }

    private void setRepertoire() {
        PieceOfMusicDto p1 = new PieceOfMusicDto();
        p1.setComposer("J.S.Bach");
        p1.setTitle("Toccata and fugue d-minor");
        repertoireSymph.add(p1);
        PieceOfMusicDto p2 = new PieceOfMusicDto();
        p2.setTitle("Symphonie IX");
        p2.setComposer("Ludwig van Beethoven");
        repertoireSymph.add(p2);

        PieceOfMusicDto carols = new PieceOfMusicDto();
        carols.setTitle("Polskie kolędy");
        carols.setComposer("Stefan Stuligrosz");
        repertoireCarols.add(carols);
    }

    /*private Concert getConcert(ConcertRoom concertRoom,Performers performers, List<PieceOfMusic> repertoire)
    {
        Concert concert = new Concert();
        concert.setAdditionalOrganisationCosts(new BigDecimal("1000.00"));
        concert.setConcertTitle("Symfoniczny Koncert Niepodległościowy");
        try {concert.setDate(DateUtils.parseDate("11/11/2019 19:00"));
        } catch (ParseException e) { e.printStackTrace();}
        concert.setRepertoire(repertoire);
        concert.setConcertPerformers(performers);
        concert.setConcertRoom(concertRoom);
        concert.setTicketCost(new BigDecimal("100.00"));
        concert.setIdConcert(1);
        return concert;
    }*/

    private void setPerformers() {
        PerformersDto performers = new PerformersDto();
        performers.setCostOfPersonnel(new BigDecimal("3000.00"));
        performers.setDetails(ORKIESTRA_SYMFONICZNA);
        performersService.create(performers);

        PerformersDto performers2 = new PerformersDto();
        performers2.setCostOfPersonnel(new BigDecimal("4000.00"));
        performers2.setDetails(ORKIESTRA_SMYCZKOWA_Z_CHOREM_I_ORGANAMI);
        performersService.create(performers2);

        PerformersDto performers3 = new PerformersDto();
        performers3.setDetails("Orkiestra smyczkowa");
        performers3.setCostOfPersonnel(new BigDecimal("1800"));
        performersService.create(performers3);

        PerformersDto performers4 = new PerformersDto();
        performers4.setDetails("Orkiestra dęta");
        performers4.setCostOfPersonnel(new BigDecimal("1000"));
        performersService.create(performers4);
    }

    private ConcertRoom setConcertRoom()
    {
        ConcertRoom concertRoom = new ConcertRoom();
        concertRoom.setConcertRoomName("Sala koncertowa Filharmonii Narodowej");
        concertRoom.setRentCosts(new BigDecimal("1000.00"));
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
        return concertRoomRepository.save(concertRoom);
    }

    private void addDiscounts()
    {
        Discount normal = new Discount();
        normal.setName("Normalny");
        normal.setPercents(0);
        discountService.addDiscount(mapper.discountToDto(normal));

        Discount smallStudent = new Discount();
        smallStudent.setName("Uczniowski");
        smallStudent.setPercents(30);
        discountService.addDiscount(mapper.discountToDto(smallStudent));

        Discount student = new Discount();
        student.setName("Studencki");
        student.setPercents(50);
        discountService.addDiscount(mapper.discountToDto(student));
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

    public void populate()
    {
        setConcertRoom();
        setPerformers();
        addDiscounts();
        setRepertoire();
        for(int i = 0 ; i<4 ; i++) testAddingConcert("2018-11-" + (10+dateAdd++) +"T19:00:00.000 UTC",
                "Symfoniczny Koncert Niepodległościowy","110.00",ORKIESTRA_SYMFONICZNA,repertoireSymph);
        for(int i = 0 ; i<4 ; i++) testAddingConcert("2018-12-" + (20+dateAdd++) +"T19:00:00.000 UTC", "Koncert kolęd",
                "120.00",ORKIESTRA_SMYCZKOWA_Z_CHOREM_I_ORGANAMI,repertoireCarols);
        try { setFutureConcerts(); }
        catch (ParseException e) { e.printStackTrace(); }
        catch (EntityNotFoundException e) { e.printStackTrace(); }

    }

    private void setFutureConcerts() throws ParseException, EntityNotFoundException {
        ConcertDto concertDto = new ConcertDto();
        concertDto.setApproved(true);
        concertDto.setConcertPerformers(ORKIESTRA_SYMFONICZNA);
        concertDto.setTicketCost(new BigDecimal("100"));
        concertDto.setAdditionalOrganisationCosts(new BigDecimal("1300.00"));
        concertDto.setDate("2019-01-20T19:00:00.000 UTC");

        concertDto.setConcertTitle("Koncert symfoniczny");
        concertDto.setRepertoire(repertoireSymph);
        concertDto.setApproved(true);
        concertRepository.save(concertMapper.dtoToConcert(concertDto));

        ConcertDto concertDto2 = new ConcertDto();
        concertDto2.setApproved(true);
        concertDto2.setConcertPerformers(ORKIESTRA_SYMFONICZNA);
        concertDto2.setTicketCost(new BigDecimal("120"));
        concertDto2.setAdditionalOrganisationCosts(new BigDecimal("1000.00"));
        concertDto2.setDate("2019-01-21T18:00:00.000 UTC");
        concertDto2.setApproved(false);
        concertDto2.setConcertTitle("Koncert symfoniczny");
        concertDto2.setRepertoire(repertoireSymph);
        concertRepository.save(concertMapper.dtoToConcert(concertDto2));
    }
}