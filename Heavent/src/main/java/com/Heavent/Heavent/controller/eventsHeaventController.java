package com.Heavent.Heavent.controller;

import com.Heavent.Heavent.modele.eventsHeavent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Heavent.Heavent.service.eventsHeaventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventsheavent")
@AllArgsConstructor
public class eventsHeaventController {
        private final eventsHeaventService eventsHeaventService;

        @RequestMapping("/create")
        public eventsHeavent create(@RequestBody  eventsHeavent eventsHeavent){
            return eventsHeaventService.save(eventsHeavent);
        }

        @GetMapping("/read")
        public List<eventsHeavent> reads(){
            return eventsHeaventService.read();
        }

        @GetMapping("/read/{mot}") //Methode pour rechercher les evenements qui contiennent un mot
        public List<eventsHeavent> reads(@PathVariable String mot){
            return eventsHeaventService.reads(mot);
        }

        @GetMapping("/readbyid/{id}")
        public ResponseEntity<eventsHeavent> read(@PathVariable long id){
            Optional<eventsHeavent> event = eventsHeaventService.read(id);
            if (event.isPresent()) {
                return new ResponseEntity<>(event.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping("/update/{id}")
        public eventsHeavent update(@PathVariable long id,  eventsHeavent eventsHeavent){
            return eventsHeaventService.update(id);
        }

        @DeleteMapping("/delete/{id}")
        public String delete(@PathVariable long id){
            return eventsHeaventService.delete(id);
        }

}
