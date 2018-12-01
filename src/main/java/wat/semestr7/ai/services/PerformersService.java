package wat.semestr7.ai.services;

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
