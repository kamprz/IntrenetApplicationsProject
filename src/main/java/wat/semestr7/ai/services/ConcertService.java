package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.ConcertDTO;
import wat.semestr7.ai.dtos.ConcertDetailedDTO;
import wat.semestr7.ai.repositories.ConcertRepository;

import java.util.List;

@Service
public class ConcertService
{
    private ConcertRepository repo;

    public ConcertService(ConcertRepository repo) {
        this.repo = repo;
    }

    public List<ConcertDTO> getAllConcerts()
    {
        List<ConcertDTO> result = repo.findAllConcerts();
        return result;
    }

    public ConcertDetailedDTO getConcertWithDetails(int id)
    {
        ConcertDetailedDTO concert = repo.findByIdConcertWithDetails(id);
        concert.setRepertoire(repo.findConcertRepertoire(concert.getConcertId()));
        return concert;
    }



}
