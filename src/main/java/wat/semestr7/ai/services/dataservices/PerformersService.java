package wat.semestr7.ai.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.PerformersRepository;

@Service
public class PerformersService
{
    private PerformersRepository performersRepository;

    public PerformersService(PerformersRepository performersRepository) {
        this.performersRepository = performersRepository;
    }
}
