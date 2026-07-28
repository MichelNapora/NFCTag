package com.nfctag.features.scan;

import java.util.UUID;


public record ScanCommand(UUID deviceToken, Double latitude, Double longitude, Double accuracy,
                          String firstname, String lastname, String mobile, UUID businessId) {}
