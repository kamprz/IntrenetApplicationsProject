package wat.semestr8.tim.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr8.tim.populating.ServiceDemo;

@RestController
public class PopulateController  {
    private ServiceDemo serviceDemo;

    public PopulateController(ServiceDemo serviceDemo) {
        this.serviceDemo = serviceDemo;
    }

    @RequestMapping("/populate")
    public void populate()
    {
        serviceDemo.populate();
    }
}
