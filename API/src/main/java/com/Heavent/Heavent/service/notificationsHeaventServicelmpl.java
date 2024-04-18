package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.notificationsHeavent;
import com.Heavent.Heavent.repository.notificationsHeaventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class notificationsHeaventServicelmpl implements notificationsHeaventService {
    private final notificationsHeaventRepository notificationsHeaventRepository;

    @Autowired
    public notificationsHeaventServicelmpl(notificationsHeaventRepository notificationsHeaventRepository) {
        this.notificationsHeaventRepository = notificationsHeaventRepository;
    }

    @Override
    public notificationsHeavent save(notificationsHeavent notificationsHeavent) {
        return notificationsHeaventRepository.save(notificationsHeavent);
    }

    @Override
    public List<notificationsHeavent> read() {
        return notificationsHeaventRepository.findAll();
    }

    @Override
    public Optional<notificationsHeavent> read(long id) {
        // Implémentez la logique pour lire une notification spécifique
        return null; // Replace null with the actual implementation
    }

    @Override
    public notificationsHeavent update(long id, notificationsHeavent updatedNotification) {
        // Implémentez la logique pour mettre à jour une notification
        return null; // Replace null with the actual implementation
    }

    @Override
    public String delete(long id) {
        // Implémentez la logique pour supprimer une notification
        return null; // Replace null with the actual implementation
    }
}