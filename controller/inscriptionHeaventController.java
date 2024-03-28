package com.Heavent.Heavent.controller;

import com.Heavent.Heavent.modele.inscriptionHeavent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.Heavent.Heavent.service.inscriptionHeaventService;

import java.util.List;

@RestController
@RequestMapping("/inscriptionheavent")
@AllArgsConstructor
public class inscriptionHeaventController {
        private final inscriptionHeaventService inscriptionHeaventService;

    @RequestMapping("/create")
    public inscriptionHeavent create(@RequestBody inscriptionHeavent inscriptionHeavent, @RequestParam String userEmail, @RequestParam long eventId) {
        return inscriptionHeaventService.save(inscriptionHeavent, userEmail, eventId);
    }

    @GetMapping("/read")
    public List<inscriptionHeavent> reads(){
        return inscriptionHeaventService.read();
    }


    @PutMapping("/update/{id}")
    public inscriptionHeavent update(@PathVariable long id, @RequestBody inscriptionHeavent inscriptionHeavent, @RequestParam String userEmail, @RequestParam long eventId) {
        return inscriptionHeaventService.update(id, userEmail, eventId);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        return inscriptionHeaventService.delete(id);
    }
}
