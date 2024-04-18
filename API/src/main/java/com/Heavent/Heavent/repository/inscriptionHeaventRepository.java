package com.Heavent.Heavent.repository;

import com.Heavent.Heavent.modele.inscriptionHeavent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface inscriptionHeaventRepository extends JpaRepository<inscriptionHeavent, Long> {
    @Query("SELECT i FROM inscriptionHeavent i WHERE i.user.email = :userEmail AND i.event.id = :eventId")
    Optional<inscriptionHeavent> findByUserEmailAndEventId(@Param("userEmail") String userEmail, @Param("eventId") long eventId);
    List<inscriptionHeavent> findByUserEmail(String email);
    ;

}
