package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.usersHeavent;
import com.Heavent.Heavent.repository.usersHeaventRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class usersHeaventServicelmpl implements usersHeaventService {

    private usersHeaventRepository usersHeaventRepository;

    public usersHeaventServicelmpl(usersHeaventRepository usersHeaventRepository) {
        this.usersHeaventRepository = usersHeaventRepository;
    }

    @Override
    public usersHeavent save(usersHeavent usersHeavent) {
        return this.usersHeaventRepository.save(usersHeavent);
    }

    @Override
    public List<usersHeavent> read() {
        return this.usersHeaventRepository.findAll();
    }

    @Override
    public List<usersHeavent> reads(String email) {
        return this.usersHeaventRepository.findAllById(Collections.singleton(email));
    }

    @Override
    public usersHeavent update(String email, usersHeavent updatedUser) {
        return usersHeaventRepository.findById(email).map(p -> {
            p.setEmail(updatedUser.getEmail());
            p.setUsername(updatedUser.getUsername());
            p.setPassword(updatedUser.getPassword());
            p.setIsAdmin(updatedUser.getIsAdmin());
            return usersHeaventRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("email not found"));
    }

    @Override
    public String delete(String email) {
        usersHeaventRepository.deleteById(email);
        return "email deleted";
    }
}
