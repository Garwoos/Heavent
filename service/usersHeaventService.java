package com.Heavent.Heavent.service;

import com.Heavent.Heavent.modele.usersHeavent;

import java.util.List;


public interface usersHeaventService {

    usersHeavent save(usersHeavent usersHeavent);

    List<usersHeavent>read();

    usersHeavent update(String email, usersHeavent usersHeavent);

    String delete(String email);

   List<usersHeavent> reads(String email);
}
