package com.Heavent.Heavent.controller;

import com.Heavent.Heavent.modele.inscriptionHeavent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Heavent.Heavent.service.inscriptionHeaventService;


import java.util.List;

@RestController
@RequestMapping("/inscriptionheavent")
public class inscriptionHeaventController {
        private final inscriptionHeaventService inscriptionHeaventService;

    @Autowired
    public inscriptionHeaventController(inscriptionHeaventService inscriptionHeaventService) {
        this.inscriptionHeaventService = inscriptionHeaventService;
    }

    @RequestMapping("/create")
    public inscriptionHeavent create(@RequestParam String userEmail, @RequestParam long eventId) {
        return inscriptionHeaventService.save(userEmail, eventId);
    }

    @GetMapping("/read")
    public List<inscriptionHeavent> reads(){
        return inscriptionHeaventService.read();
    }

    @GetMapping("/find")
    public Long findInscriptionIdByUserEmailAndEventId(@RequestParam String userEmail, @RequestParam long eventId) {
        return inscriptionHeaventService.findInscriptionIdByUserEmailAndEventId(userEmail, eventId);
    }

    @GetMapping("/geteventsforuser/{email}")
    public List<inscriptionHeavent> getEventsForUser(@PathVariable String email) {
        return inscriptionHeaventService.getEventsForUser(email);
    }


    @PutMapping("/update/{id}")
    public inscriptionHeavent update(@PathVariable long id, @RequestBody inscriptionHeavent inscriptionHeavent, @RequestParam String userEmail, @RequestParam long eventId) {
        return inscriptionHeaventService.update(id, userEmail, eventId);
    }



    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        inscriptionHeaventService.delete(id);
    }

}


