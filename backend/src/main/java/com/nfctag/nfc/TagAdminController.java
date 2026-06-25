package com.nfctag.nfc;

import com.nfctag.nfc.dto.CreateTagRequest;
import com.nfctag.nfc.dto.TagAdminDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administration des tags NFC. Zone protégée (futur SSO).
 */
@RestController
@RequestMapping("/api/admin/tags")
public class TagAdminController {

    private final NfcService service;

    public TagAdminController(NfcService service) {
        this.service = service;
    }

    @GetMapping
    public List<TagAdminDto> list() {
        return service.listForAdmin();
    }

    @PostMapping
    public TagAdminDto create(@Valid @RequestBody CreateTagRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        service.archive(id);
    }
}
