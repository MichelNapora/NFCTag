package com.nfctag.features.scan;

import com.nfctag.features.presence.Presence;
import com.nfctag.features.tag.Tag;
import com.nfctag.features.technician.Technician;

public record ScanResult(Technician technician, Tag tag, Presence presence,
                         LocationCheck location, ScanAction action) {}