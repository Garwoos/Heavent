package com.Heavent.Heavent.repository;

import com.Heavent.Heavent.modele.eventsHeavent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface eventsHeaventRepository extends JpaRepository<eventsHeavent, Long> {
    List<eventsHeavent> findByNameContaining(String mot);

}
