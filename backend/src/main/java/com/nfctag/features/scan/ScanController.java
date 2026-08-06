package com.nfctag.features.scan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final DeviceCookie deviceCookie;

    public ScanController(ScanService scanService, ScanMapper scanMapper, DeviceCookie deviceCookie){
        this.scanService=scanService;
        this.scanMapper=scanMapper;
        this.deviceCookie=deviceCookie;
    }

    @PostMapping("/scan/{scanToken}")
    public ScanResponseDTO scan(@PathVariable UUID scanToken,
                                @Valid @RequestBody ScanRequestDTO request,
                                HttpServletRequest httpRequest,
                                HttpServletResponse httpResponse){
        ScanCommand command = scanMapper.toCommand(request, deviceCookie.read(httpRequest));
        ScanResult result = scanService.scan(scanToken, command);
        deviceCookie.write(httpResponse, result.technician().getDeviceToken());
        return scanMapper.toDto(result);
    }
}