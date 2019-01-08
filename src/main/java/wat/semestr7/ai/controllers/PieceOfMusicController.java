package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.PieceOfMusicDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr7.ai.services.dataservices.PieceOfMusicService;

import java.util.List;

@RestController
public class PieceOfMusicController
{
    private PieceOfMusicService service;

    public PieceOfMusicController(PieceOfMusicService pieceOfMusicService) {
        this.service = pieceOfMusicService;
    }

    @GetMapping("/admin/piece")
    public ResponseEntity<List<PieceOfMusicDto>> getAllPieces()
    {
        return ResponseEntity.ok().body(service.getAll());
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},value = "/admin/piece")
    public void postPieceOfMusic(@RequestBody PieceOfMusicDto dto) throws WrongEntityInRequestBodyException
    {
        checkIfRequestBodyIsCorrect(dto);
        service.create(dto);
    }

    @DeleteMapping("/admin/piece/{id}")
    public void deletePieceOfMusic(@PathVariable Integer id) throws EntityNotFoundException {
        service.delete(id);
    }

    private void checkIfRequestBodyIsCorrect(PieceOfMusicDto dto) throws WrongEntityInRequestBodyException {
        if(dto.getComposer() == null || dto.getComposer().isEmpty()) throw new WrongEntityInRequestBodyException("Composer of a piece is not set");
        if(dto.getTitle() == null || dto.getTitle().isEmpty()) throw new WrongEntityInRequestBodyException("Title of a piece is not set");
    }
}
