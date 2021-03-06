package wat.semestr8.tim.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.PieceOfMusicDto;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.services.dataservices.PieceOfMusicService;

import javax.validation.Valid;
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
    public void postPieceOfMusic(@RequestBody @Valid PieceOfMusicDto dto)
    {
        service.create(dto);
    }

    @DeleteMapping("/admin/piece/{id}")
    public void deletePieceOfMusic(@PathVariable Integer id) throws EntityNotFoundException {
        service.delete(id);
    }
}
