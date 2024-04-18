package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.eventsHeavent;

import java.util.List;
import java.util.Optional;

public interface eventsHeaventService {

    eventsHeavent save(eventsHeavent eventsHeavent);

    List<eventsHeavent>read();

    List<eventsHeavent> reads(String mot);

    Optional<eventsHeavent> read(long id);

    List<eventsHeavent> search(String query);
    // trouver le mail dans l'attribut mailUser
    String readmailuser(int id);

    eventsHeavent update(long id, eventsHeavent updatedEvent);

    String delete(long id);

    List<eventsHeavent> getalleventfromuser(String email);
}


