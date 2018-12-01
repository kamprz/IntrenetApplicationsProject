package wat.semestr7.ai.controllers;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.ConcertDTO;
import wat.semestr7.ai.dtos.ConcertDetailedDTO;
import wat.semestr7.ai.services.ConcertService;

import java.util.List;

@RestController
public class ConcertController
{
    private ConcertService service;

    public ConcertController(ConcertService service) {
        this.service = service;
    }

    @GetMapping("/concerts")
    public ResponseEntity<List<ConcertDTO>> getAllConcerts()
    {
        return new ResponseEntity<>(service.getAllConcerts(), HttpStatus.OK);
    }

    @GetMapping("/concerts/{id}")
    public ResponseEntity<ConcertDetailedDTO> getConcertWithRepertoire(@PathVariable("id") int id)
    {
        return new ResponseEntity<>(service.getConcertWithDetails(id),HttpStatus.OK);
    }
}
