package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/presences/search")
    public PagedModel<PresenceDTO> search(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Presence> result = this.presenceService.search(year, state, query, page, size);

        return new PagedModel<>(result.map(presenceMapper::toDto));
    }

    @GetMapping("/presences/search-meta")
    public SearchMetaDTO searchMeta(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String query) {
        return this.presenceService.searchMeta(year, query);
    }

    @GetMapping("/presences/ongoing")
    public List<PresenceDTO> findOngoing() {
        return this.presenceService.findOngoing().stream().map(presenceMapper::toDto).toList();
    }

    @GetMapping("/presences/export")
    public List<PresenceDTO> export(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String query) {
        return this.presenceService.searchAll(year, state, query).stream()
                .map(presenceMapper::toDto)
                .toList();
    }

    @GetMapping("/presences/count")
    public long count() {
        return this.presenceService.count();
    }

    @DeleteMapping("/presences/{id}")
    public void delete(@PathVariable UUID id) {
        this.presenceService.delete(id);
    }
}