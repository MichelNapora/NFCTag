package com.nfctag.wing;

import com.nfctag.wing.dto.CreateWingRequest;
import com.nfctag.wing.dto.WingAdminDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administration des ailes. Zone protégée (futur SSO).
 */
@RestController
@RequestMapping("/api/admin/wings")
public class WingAdminController {

    private final WingService service;

    public WingAdminController(WingService service) {
        this.service = service;
    }

    @GetMapping
    public List<WingAdminDto> list() {
        return service.listForAdmin();
    }

    @PostMapping
    public WingAdminDto create(@Valid @RequestBody CreateWingRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        service.archive(id);
    }
}
