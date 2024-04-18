package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.notificationsHeavent;

import java.util.List;
import java.util.Optional;

public interface notificationsHeaventService {

    notificationsHeavent save(notificationsHeavent notificationsHeavent);

    List<notificationsHeavent> read();

    Optional<notificationsHeavent> read(long id);

    notificationsHeavent update(long id, notificationsHeavent updatedNotification);

    String delete(long id);
}