package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.eventsHeavent;
import com.Heavent.Heavent.modele.usersHeavent;
import com.Heavent.Heavent.repository.eventsHeaventRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
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
    public String readmailuser(int id) {
        Optional<eventsHeavent> event = eventsHeaventRepository.findById((long) id);
        if (event.isPresent()) {
            return event.get().getUser();
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public List<eventsHeavent> search(String query) {
        return eventsHeaventRepository.findByNameContaining(query);
    }

    @Override
    public List<eventsHeavent> getalleventfromuser(String email) {
        return this.eventsHeaventRepository.findAllByUser(email);
    }

    @Override
    public eventsHeavent update(long id, eventsHeavent updatedEvent) {
        return eventsHeaventRepository.findById(id).map(p -> {
            p.setName(updatedEvent.getName());
            p.setDescription(updatedEvent.getDescription());
            p.setDate(updatedEvent.getDate());
            p.setLocation(updatedEvent.getLocation());
            p.setPlaces(updatedEvent.getPlaces());
            p.setUser(updatedEvent.getUser());
            p.setPrix(updatedEvent.getPrix());
            return eventsHeaventRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("id not found"));
    }

    @Override
    public String delete(long id) {
        eventsHeaventRepository.deleteById(id);
        return "id deleted";
    }
}
