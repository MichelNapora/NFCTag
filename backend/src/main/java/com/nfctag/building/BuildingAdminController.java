package com.nfctag.building;

import com.nfctag.building.dto.BuildingAdminDto;
import com.nfctag.building.dto.CreateBuildingRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administration des bâtiments. Zone protégée (futur SSO).
 */
@RestController
@RequestMapping("/api/admin/buildings")
public class BuildingAdminController {

    private final BuildingService service;

    public BuildingAdminController(BuildingService service) {
        this.service = service;
    }

    @GetMapping
    public List<BuildingAdminDto> list() {
        return service.listForAdmin();
    }

    @PostMapping
    public BuildingAdminDto create(@Valid @RequestBody CreateBuildingRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        service.archive(id);
    }
}
