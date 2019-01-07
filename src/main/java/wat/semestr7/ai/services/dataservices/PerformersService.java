package wat.semestr7.ai.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.PerformersDto;
import wat.semestr7.ai.dtos.mappers.EntityToDtoMapper;
import wat.semestr7.ai.entities.Performers;
import wat.semestr7.ai.repositories.PerformersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerformersService
{
    private PerformersRepository performersRepository;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public PerformersService(PerformersRepository performersRepository) {
        this.performersRepository = performersRepository;
    }

    public List<PerformersDto> getAll() {
        return performersRepository.findAll()
                .stream()
                .map(p -> mapper.performersToDto(p))
                .collect(Collectors.toList());
    }

    public Performers create(PerformersDto dto) {
        return performersRepository.save(mapper.dtoToPerformers(dto));
    }

    public void delete(int id) {
        Optional<Performers> performerOpt = performersRepository.findById(id);
        Performers performer = performerOpt.orElseThrow(() -> new EntityNotFoundException());
        performersRepository.delete(performer);
    }
}
