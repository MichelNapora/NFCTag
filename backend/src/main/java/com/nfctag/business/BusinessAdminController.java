package com.nfctag.business;

import com.nfctag.business.dto.BusinessAdminDto;
import com.nfctag.business.dto.CreateBusinessRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administration des sociétés. Zone protégée (futur SSO).
 */
@RestController
@RequestMapping("/api/admin/businesses")
public class BusinessAdminController {

    private final BusinessService service;

    public BusinessAdminController(BusinessService service) {
        this.service = service;
    }

    @GetMapping
    public List<BusinessAdminDto> list() {
        return service.listForAdmin();
    }

    @PostMapping
    public BusinessAdminDto create(@Valid @RequestBody CreateBusinessRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        service.archive(id);
    }
}
