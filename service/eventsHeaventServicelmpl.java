package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.eventsHeavent;
import com.Heavent.Heavent.repository.eventsHeaventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class eventsHeaventServicelmpl implements eventsHeaventService {

    private final eventsHeaventRepository eventsHeaventRepository;

    public eventsHeaventServicelmpl(com.Heavent.Heavent.repository.eventsHeaventRepository eventsHeaventRepository) {
        this.eventsHeaventRepository = eventsHeaventRepository;
    }


    @Override
    public eventsHeavent save(eventsHeavent eventsHeavent) {
        return eventsHeaventRepository.save(eventsHeavent);
    }

    @Override
    public List<eventsHeavent> read() {
        return eventsHeaventRepository.findAll();
    }

    @Override
    public List<eventsHeavent> reads(String mot) {
        return this.eventsHeaventRepository.findByNameContaining(mot);
    }

    @Override
    public Optional<eventsHeavent> read(long id) {
        return eventsHeaventRepository.findById(id);
    }

    @Override
    public eventsHeavent update(long id) {
        return eventsHeaventRepository.findById(id).map(p -> {
            p.setName(p.getName());
            p.setDescription(p.getDescription());
            p.setDate(p.getDate());
            p.setLocation(p.getLocation());
            p.setPlaces(p.getPlaces());
            p.setUser(p.getUser());
            p.setPrix(p.getPrix());
            return eventsHeaventRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("id not found"));
    }

    @Override
    public String delete(long id) {
        eventsHeaventRepository.deleteById(id);
        return "id deleted";
    }
}
