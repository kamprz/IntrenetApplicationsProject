package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.mappers.ConcertMapper;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.repositories.ConcertRepository;
import wat.semestr7.ai.repositories.PieceOfMusicRepository;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
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


    public List<ConcertDto> getAllConcerts() {
        return concertRepo.findAll().stream().map(c -> concertMapper.concertToDto(c)).collect(Collectors.toList());
    }

    public void addConcert(ConcertDto concertDto) throws ParseException {
        concertRepo.save(concertMapper.dtoToConcert(concertDto));
    }

    public void updateConcert(ConcertDto dto) throws ParseException {
        concertRepo.save(concertMapper.dtoToConcert(dto));
    }

    public void deleteConcert(ConcertDto dto) throws ParseException {
        concertRepo.delete(concertMapper.dtoToConcert(dto));
    }
}
