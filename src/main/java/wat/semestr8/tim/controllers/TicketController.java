package wat.semestr8.tim.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr8.tim.dtos.AndroidTicketDto;
import wat.semestr8.tim.dtos.TicketDto;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.services.dataservices.TicketService;
import wat.semestr8.tim.services.ticketsending.TicketSendingService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;


@RestController("ticket")
public class TicketController
{
    private TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<List<AndroidTicketDto>> getAllTicketsOfAndroidUser(@RequestParam String id){
        return ResponseEntity.ok().body(service.getAllTicketsByUserId(id));
    }
}
