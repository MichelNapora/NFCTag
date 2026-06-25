package com.nfctag.scan;

import com.nfctag.scan.dto.LookupRequest;
import com.nfctag.scan.dto.RegisterRequest;
import com.nfctag.scan.dto.ScanRequest;
import com.nfctag.scan.dto.ScanResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints publics utilisés par la page de scan (téléphone du technicien).
 */
@RestController
@RequestMapping("/api/scan")
public class ScanController {

    private final ScanService scanService;

    public ScanController(ScanService scanService) {
        this.scanService = scanService;
    }

    /** Scan : présente le jeton appareil s'il existe. */
    @PostMapping
    public ScanResult scan(@Valid @RequestBody ScanRequest request) {
        return scanService.scan(request);
    }

    /** Repli n°1 : identification par numéro de mobile. */
    @PostMapping("/lookup")
    public ScanResult lookup(@Valid @RequestBody LookupRequest request) {
        return scanService.lookup(request);
    }

    /** Repli n°2 : premier passage, choix de la société. */
    @PostMapping("/register")
    public ScanResult register(@Valid @RequestBody RegisterRequest request) {
        return scanService.register(request);
    }
}
