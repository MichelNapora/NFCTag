package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PresenceController {
    @Autowired
    private PresenceService presenceService;

    @Autowired
    private PresenceMapper presenceMapper;

    @GetMapping("/presences")
    public List<PresenceDTO> findAll() {
        return this.presenceService.findAll().stream().map(presenceMapper::toDto).toList();
    }
}
