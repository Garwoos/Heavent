package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.inscriptionHeavent;

import java.util.List;

public interface inscriptionHeaventService {

    inscriptionHeavent save(String userEmail, long eventId);


    List<inscriptionHeavent>read();

    List<inscriptionHeavent> getEventsForUser(String email);

    inscriptionHeavent update(long id, String userEmail, long eventId);

    void delete(long id);

    Long findInscriptionIdByUserEmailAndEventId(String userEmail, long eventId);


}
