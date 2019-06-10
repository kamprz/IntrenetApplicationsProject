package wat.semestr8.tim.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.ConcertDetailsDto;
import wat.semestr8.tim.dtos.mappers.ConcertMapper;
import wat.semestr8.tim.dtos.ConcertDto;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.entities.Concert;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Ticket;
import wat.semestr8.tim.exceptions.customexceptions.ConcertAlreadyApprovedException;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.ConcertRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertService
{
    private ConcertRepository concertRepo;
    private TicketService ticketService;
    private ConcertRoomService concertRoomService;
    private ConcertMapper concertMapper;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public ConcertService(ConcertRepository concertRepo, TicketService ticketService, ConcertRoomService concertRoomService, ConcertMapper concertMapper, PieceOfMusicService pieceOfMusicService) {
        this.concertRepo = concertRepo;
        this.ticketService = ticketService;
        this.concertRoomService = concertRoomService;
        this.concertMapper = concertMapper;
    }

    public ConcertDto getConcertDto(int id) throws EntityNotFoundException
    {
        Optional<Concert> concertOpt = concertRepo.findById(id);
        Concert concert = concertOpt.orElseThrow(() -> new EntityNotFoundException("Such concert does not exist"));
        return concertMapper.concertToDto(concert);
    }

    public Concert getConcert(int id) throws EntityNotFoundException
    {
        Optional<Concert> concertOpt = concertRepo.findById(id);
        return concertOpt.orElseThrow(() -> new EntityNotFoundException("Such concert does not exist"));
    }

    public List<ConcertDto> getAllConcertsDto() {
        return concertRepo.findAll().stream().map(c -> concertMapper.concertToDto(c)).collect(Collectors.toList());
    }

    public List<Concert> getAllConcerts(){
        return concertRepo.findAll();
    }

    public void addConcert(ConcertDto concertDto) throws ParseException, EntityNotFoundException {
        Concert mappedConcert = concertMapper.dtoToConcert(concertDto);
        concertRepo.save(mappedConcert);
    }

    public void deleteConcert(int id) throws EntityNotFoundException {
        Optional<Concert> concertOpt = concertRepo.findById(id);
        Concert concert = concertOpt.orElseThrow(() -> new EntityNotFoundException("Such concert does not exist"));
        concertRepo.delete(concert);
    }

    public List<ConcertDto> getNotApprovedConcerts()
    {
        return concertRepo.findAll().stream()
                .filter(c -> (!c.isApproved()))
                .filter(this::isBeforeConcertDate)
                .map(c -> concertMapper.concertToDto(c))
                .collect(Collectors.toList());
    }

    public List<ConcertDto> getApprovedConcerts()
    {
        return concertRepo.findAll().stream()
                .filter(Concert::isApproved)
                .filter(this::isBeforeConcertDate)
                .map(c -> concertMapper.concertToDto(c))
                .collect(Collectors.toList());
    }

    private boolean isBeforeConcertDate(Concert c)
    {
        return new Date().before(c.getDate());
    }

    public BigDecimal getConcertTicketPrice(int concertId)
    {
        return concertRepo.getConcertPriceByIdConcert(concertId);
    }

    public Concert getConcertByPurchase(Purchase purchase) throws EntityNotFoundException
    {
        Ticket ticket = ticketService.getFirstTicketByPurchase(purchase);
        return getConcert(ticket.getConcert().getIdConcert());
    }

    public void approveConcert(int id) throws EntityNotFoundException {
        Optional<Concert> concertOpt = concertRepo.findById(id);
        Concert concert = concertOpt.orElseThrow(() -> new EntityNotFoundException("Such concert does not exist"));
        concert.setApproved(true);
        concertRepo.save(concert);
    }

    public List<ConcertDetailsDto> getConcertDetailDtoList()
    {
        return concertRepo.getConcertDetailsList(new Date()).stream().map(c -> mapper.detailsToDto(c)).collect(Collectors.toList());
    }

    public void deleteNotApprovedConcert(int id) throws EntityNotFoundException, ConcertAlreadyApprovedException {
        Optional<Concert> concertOpt = concertRepo.findById(id);
        Concert concert = concertOpt.orElseThrow(() -> new EntityNotFoundException("Such concert does not exist"));
        if(!getConcert(id).isApproved()) concertRepo.delete(concert);
        else throw new ConcertAlreadyApprovedException("This concert is already approved");
    }

    public void saveConcert(Concert concert)
    {
        concertRepo.save(concert);
    }
}
