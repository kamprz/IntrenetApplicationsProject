package wat.semestr8.tim.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.DiscountDto;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.entities.Discount;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.DiscountRepository;

import java.util.Comparator;
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
        Discount discount = repo.getDiscountByName(name);
        if (discount == null) throw new EntityNotFoundException();
        else return discount;
    }

    public Discount getById(int id) throws EntityNotFoundException
    {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException());
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

    public void delete(int id) throws EntityNotFoundException {
        Optional<Discount> opt = repo.findById(id);
        Discount performer = opt.orElseThrow(() -> new EntityNotFoundException());
        repo.delete(performer);
    }
}
