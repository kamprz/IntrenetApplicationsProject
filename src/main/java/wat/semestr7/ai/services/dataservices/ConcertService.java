package wat.semestr7.ai.services.dataservices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.mappers.ConcertMapper;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.repositories.ConcertRepository;
import wat.semestr7.ai.repositories.PieceOfMusicRepository;

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
    private PieceOfMusicRepository pomRepo;
    private ConcertMapper concertMapper;

    public ConcertService(ConcertRepository concertRepo,PieceOfMusicRepository pomRepo, ConcertMapper concertMapper) {
        this.concertRepo = concertRepo;
        this.pomRepo = pomRepo;
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

    public List<ConcertDto> getAllConcerts() {
        return concertRepo.findAll().stream().map(c -> concertMapper.concertToDto(c)).collect(Collectors.toList());
    }

    public void addConcert(ConcertDto concertDto) throws ParseException {
        Optional<Concert> concert = concertRepo.findById(concertDto.getIdConcert());
        Concert mappedConcert = concertMapper.dtoToConcert(concertDto);
        System.out.println(concert);
        System.out.println(mappedConcert);
        if(concert.isPresent())
        {
            if(concert.get().equals(mappedConcert))
            {
                System.out.println("Ten sam koncert");
                return;
            }
        }
        concertRepo.save(mappedConcert);
    }

    public void updateConcert(ConcertDto dto) throws ParseException {
        concertRepo.save(concertMapper.dtoToConcert(dto));
    }

    public void deleteConcert(ConcertDto dto) throws ParseException {
        concertRepo.delete(concertMapper.dtoToConcert(dto));
    }

    public List<ConcertDto> getNotApprovedConcerts()
    {
        return concertRepo.findAll().stream()
                .filter(c -> (!c.isApproved()))
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
}
