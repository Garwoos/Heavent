package com.Heavent.Heavent.controller;

import com.Heavent.Heavent.modele.notificationsHeavent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Heavent.Heavent.service.notificationsHeaventService;

import java.util.List;

@RestController
@RequestMapping("/notificationsheavent")
public class notificationsHeaventController {
    private final notificationsHeaventService notificationsHeaventService;

    @Autowired
    public notificationsHeaventController(notificationsHeaventService notificationsHeaventService) {
        this.notificationsHeaventService = notificationsHeaventService;
    }

    @PostMapping("/create")
    public notificationsHeavent create(@RequestBody notificationsHeavent newNotification) {
        return notificationsHeaventService.save(newNotification);
    }

    @GetMapping("/read")
    public List<notificationsHeavent> read() {
        return notificationsHeaventService.read();
    }
}
