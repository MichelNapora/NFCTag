package com.nfctag.features.scan;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ScanController {

    private final ScanService scanService;

    public ScanController(ScanService scanService){
        this.scanService=scanService;
    }

    @PostMapping("/scan/{scanToken}")
    public ScanResponseDTO scan(@PathVariable UUID scanToken, @Valid @RequestBody ScanRequestDTO request){
        return scanService.scan(scanToken, request);
    }
}