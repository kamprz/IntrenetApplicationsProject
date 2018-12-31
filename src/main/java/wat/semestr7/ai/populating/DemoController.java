package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.services.ServiceDemo;

@RestController
public class DemoController
{
    private ServiceDemo serviceDemo;

    public DemoController(ServiceDemo serviceDemo)
    {
        this.serviceDemo = serviceDemo;
    }

    @GetMapping("demo")
    public Concert demo()
    {
        return serviceDemo.populate();
    }

}
