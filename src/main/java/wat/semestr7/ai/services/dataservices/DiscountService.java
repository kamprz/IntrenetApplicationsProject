package wat.semestr7.ai.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.DiscountDto;
import wat.semestr7.ai.dtos.mappers.EntityToDtoMapper;
import wat.semestr7.ai.entities.Discount;
import wat.semestr7.ai.entities.Performers;
import wat.semestr7.ai.repositories.DiscountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    private DiscountRepository repo;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public DiscountService(DiscountRepository repo) {
        this.repo = repo;
    }

    public int getDiscountPercentsByName(String name)
    {
        return repo.getDiscountByName(name).getPercents();
    }

    public Discount getByName(String name) throws EntityNotFoundException
    {
        return repo.findById(name).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<DiscountDto> getAll() {
        return repo.findAll()
                .stream()
                .map(d -> mapper.discountToDto(d))
                .sorted(Comparator.comparingInt(DiscountDto::getPercents))
                .collect(Collectors.toList());
    }

    public void addDiscount(DiscountDto dto)
    {
        repo.save(mapper.dtoToDiscount(dto));
    }

    public void delete(String name)
    {
        Optional<Discount> opt = repo.findById(name);
        Discount performer = opt.orElseThrow(() -> new EntityNotFoundException());
        repo.delete(performer);
    }
}
