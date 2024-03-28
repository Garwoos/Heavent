package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.inscriptionHeavent;

import java.util.List;

public interface inscriptionHeaventService {

    inscriptionHeavent save(inscriptionHeavent inscriptionHeavent, String userEmail, long eventId);


    List<inscriptionHeavent>read();

    inscriptionHeavent update(long id, String userEmail, long eventId);

    String delete(long id);
}
