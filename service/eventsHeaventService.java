package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.eventsHeavent;

import java.util.List;
import java.util.Optional;

public interface eventsHeaventService {

    eventsHeavent save(eventsHeavent eventsHeavent);

    List<eventsHeavent>read();

    List<eventsHeavent> reads(String mot);

    Optional<eventsHeavent> read(long id);

    eventsHeavent update(long id);

    String delete(long id);

}
