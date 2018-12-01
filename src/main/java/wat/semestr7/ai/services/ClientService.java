package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.ClientRepository;

@Service
public class ClientService
{
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
