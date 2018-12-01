package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Sponsor;
import wat.semestr7.ai.services.SponsorService;

@RestController
public class SponsorController
{
    private SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }
}
