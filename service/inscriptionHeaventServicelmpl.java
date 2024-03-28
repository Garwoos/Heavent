package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.inscriptionHeavent;
import com.Heavent.Heavent.repository.inscriptionHeaventRepository;
import com.Heavent.Heavent.repository.usersHeaventRepository;
import com.Heavent.Heavent.repository.eventsHeaventRepository;
import com.Heavent.Heavent.modele.usersHeavent;
import com.Heavent.Heavent.modele.eventsHeavent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class inscriptionHeaventServicelmpl implements inscriptionHeaventService {
    private final inscriptionHeaventRepository inscriptionHeaventRepository;
    private final usersHeaventRepository usersHeaventRepository;
    private final eventsHeaventRepository eventsHeaventRepository;

    public inscriptionHeaventServicelmpl(inscriptionHeaventRepository inscriptionHeaventRepository, usersHeaventRepository usersHeaventRepository, eventsHeaventRepository eventsHeaventRepository) {
        this.inscriptionHeaventRepository = inscriptionHeaventRepository;
        this.usersHeaventRepository = usersHeaventRepository;
        this.eventsHeaventRepository = eventsHeaventRepository;
    }
    @Override
    public inscriptionHeavent save(inscriptionHeavent inscriptionHeavent, String userEmail, long eventId) {
        usersHeavent user = usersHeaventRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        eventsHeavent event = eventsHeaventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        // Vérifiez si le nombre de places disponibles est supérieur à zéro
        if (event.getPlaces() > 0) {
            // Décrémentez le nombre de places disponibles
            event.setPlaces(event.getPlaces() - 1);
            eventsHeaventRepository.save(event);

            inscriptionHeavent.setUser(user);
            inscriptionHeavent.setEvent(event);
            return inscriptionHeaventRepository.save(inscriptionHeavent);
        } else {
            throw new RuntimeException("No more places available for this event");
        }
    }

    @Override
    public List<inscriptionHeavent> read() {
        return inscriptionHeaventRepository.findAll();
    }

    @Override
    public inscriptionHeavent update(long id, String userEmail, long eventId) {
        usersHeavent user = usersHeaventRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        eventsHeavent event = eventsHeaventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        return inscriptionHeaventRepository.findById(id).map(p -> {
            p.setUser(user);
            p.setEvent(event);
            return inscriptionHeaventRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Inscription not found"));
    }

    @Override
    public String delete(long id) {
        inscriptionHeaventRepository.deleteById(id);
        return "id deleted";
    }
}
