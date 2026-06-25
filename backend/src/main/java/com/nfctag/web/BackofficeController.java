package com.nfctag.web;

import com.nfctag.dto.BackofficeDtos.*;
import com.nfctag.service.BackofficeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints du back-office (consultation des interventions et statistiques).
 * NB : l'authentification Windows sera branchée ici dans un second temps.
 */
@RestController
@RequestMapping("/api/backoffice")
public class BackofficeController {

    private final BackofficeService backofficeService;

    public BackofficeController(BackofficeService backofficeService) {
        this.backofficeService = backofficeService;
    }

    @GetMapping("/presences")
    public List<PresenceView> presences() {
        return backofficeService.listPresences();
    }

    @GetMapping("/stats")
    public Stats stats() {
        return backofficeService.computeStats();
    }
}
