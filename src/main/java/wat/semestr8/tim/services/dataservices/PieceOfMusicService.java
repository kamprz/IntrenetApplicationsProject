package wat.semestr8.tim.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.PieceOfMusicDto;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.entities.PieceOfMusic;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.PieceOfMusicRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PieceOfMusicService
{
    private PieceOfMusicRepository pieceOfMusicRepository;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public PieceOfMusicService(PieceOfMusicRepository pieceOfMusicRepository) {
        this.pieceOfMusicRepository = pieceOfMusicRepository;
    }

    public void create(PieceOfMusicDto dto) {
    pieceOfMusicRepository.save(mapper.dtoToPieceOfMusic(dto));
    }

    public PieceOfMusic createAndReturn(PieceOfMusicDto dto) {
        return pieceOfMusicRepository.save(mapper.dtoToPieceOfMusic(dto));
    }

    public void delete(int id) throws EntityNotFoundException {
        Optional<PieceOfMusic> opt = pieceOfMusicRepository.findById(id);
        PieceOfMusic performer = opt.orElseThrow(() -> new EntityNotFoundException());
        pieceOfMusicRepository.delete(performer);
    }

    public List<PieceOfMusicDto> getAll() {
        return pieceOfMusicRepository.findAll().stream().map(p -> mapper.pieceOfMusicToDto(p)).collect(Collectors.toList());
    }

    public PieceOfMusic getById(int id) throws EntityNotFoundException {
        return pieceOfMusicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public PieceOfMusic getByNameAndComposer(String name, String composer) throws EntityNotFoundException {
        PieceOfMusic pom = pieceOfMusicRepository.findFirstByTitlePieceAndComposer(name,composer);
        if(pom == null) throw new EntityNotFoundException();
        else return pom;
    }
}
