package wat.semestr7.ai.populating;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.populating.ServiceDemo;

@RestController
public class DemoController
{
    private ServiceDemo serviceDemo;

    public DemoController(ServiceDemo serviceDemo)
    {
        this.serviceDemo = serviceDemo;
    }

    @GetMapping("/demo")
    public ConcertDto demo()
    {
        serviceDemo.addUser();
        return serviceDemo.populate();
    }

    @RequestMapping("/add-user")
    public void addUser()
    {
        serviceDemo.addUser();
    }

}
