package com.Heavent.Heavent.controller;

import  com.Heavent.Heavent.modele.usersHeavent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.Heavent.Heavent.service.usersHeaventService;

import java.util.List;

@RestController
@RequestMapping("/usersheavent")
@AllArgsConstructor
public class usersHeaventController {
    private final usersHeaventService usersHeaventService;

    @RequestMapping("/create")
    public usersHeavent create(@RequestBody  usersHeavent usersHeavent){
        //si le mail existe déjà
        if (usersHeaventService.reads(usersHeavent.getEmail()).size() > 0){
            return null;
        }
        return usersHeaventService.save(usersHeavent);
    }

    @GetMapping("/read")
    public List<usersHeavent> reads(){
        return usersHeaventService.read();
    }

    @GetMapping("/read/{email}")
    public List<usersHeavent> reads(@PathVariable String email){
        return usersHeaventService.reads(email);
    }

    @PutMapping("/update/{email}")
    public usersHeavent update(@PathVariable String email, @RequestBody usersHeavent usersHeavent){
        return usersHeaventService.update(email, usersHeavent);
    }

    @DeleteMapping("/delete/{email}")
    public String delete(@PathVariable String email){
        return usersHeaventService.delete(email);
    }

}
