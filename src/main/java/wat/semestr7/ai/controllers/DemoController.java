package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Seat;
import wat.semestr7.ai.services.ServiceDemo;

import java.util.List;

@RestController
public class DemoController
{
    private ServiceDemo serviceDemo;

    public DemoController(ServiceDemo serviceDemo)
    {
        this.serviceDemo = serviceDemo;
    }

    @GetMapping("demo")
    public List<String> demo()
    {
        return serviceDemo.testConcertRoom();
    }
}
