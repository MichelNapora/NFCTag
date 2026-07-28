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
    private final ScanMapper scanMapper;

    public ScanController(ScanService scanService, ScanMapper scanMapper){
        this.scanService=scanService;
        this.scanMapper=scanMapper;
    }

    @PostMapping("/scan/{scanToken}")
    public ScanResponseDTO scan(@PathVariable UUID scanToken, @Valid @RequestBody ScanRequestDTO request){
        ScanResult result = scanService.scan(scanToken, scanMapper.toCommand(request));
        return scanMapper.toDto(result);
    }
}