package com.nfctag.features.scan;

import com.nfctag.features.business.Business;
import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.business.BusinessRepository;
import com.nfctag.features.presence.Presence;
import com.nfctag.features.presence.PresenceRepository;
import com.nfctag.features.tag.Tag;
import com.nfctag.features.tag.TagNotFoundException;
import com.nfctag.features.tag.TagRepository;
import com.nfctag.features.technician.Technician;
import com.nfctag.features.technician.TechnicianNotFoundException;
import com.nfctag.features.technician.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScanService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private GeoDistanceCalculator geoDistanceCalculator;

    @Value("${nfctag.proximity-meters}") private double proximityMeters;
    @Value("${nfctag.max-accuracy-meters}") private double maxAccuracyMeters;

    public ScanResponseDTO scan(UUID scanToken, ScanRequestDTO request){

        Tag tag = tagRepository.findByScanToken(scanToken).orElseThrow(() -> new TagNotFoundException("Tag not found"));

        Technician technician = resolveTechnician(request);

        boolean locationVerified = isLocationVerified(tag, request);

        if (tag.getLatitude() == null && request.getLatitude() != null && request.getLongitude() != null
                && request.getAccuracy() != null && request.getAccuracy() <= maxAccuracyMeters) {
            tag.setLatitude(request.getLatitude());
            tag.setLongitude(request.getLongitude());
            tagRepository.save(tag);
            locationVerified = true;
        }

        Optional<Presence> open = presenceRepository.findByTechnicianIdAndTagIdAndDepartedAtIsNull(technician.getId(), tag.getId());

        Presence presence;
        ScanAction action;

        if (open.isPresent()) {
            presence = open.get();
            presence.setDepartedAt(OffsetDateTime.now());
            action = ScanAction.DEPARTURE;
        } else {
            presence = new Presence(technician, tag, OffsetDateTime.now(), locationVerified);
            action = ScanAction.ARRIVAL;
        }

        presenceRepository.save(presence);

        return new ScanResponseDTO(
                technician.getDeviceToken(),
                technician.getFirstname() + " " + technician.getLastname(),
                tag.getWing().getBuilding().getName(),
                tag.getWing().getName(),
                presence.getArrivedAt(),
                presence.getDepartedAt(),
                locationVerified,
                action
        );
    }

    private Technician resolveTechnician(ScanRequestDTO request){
        if (request.getDeviceToken() != null) {
            return technicianRepository.findByDeviceToken(request.getDeviceToken())
                    .orElseThrow(() -> new TechnicianNotFoundException("Token not found"));
        }

        Optional<Technician> existing = technicianRepository.findByMobile(request.getMobile());
        if (existing.isPresent()) {
            return existing.get();
        }

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        Technician technician = new Technician(
                request.getFirstname(),
                request.getLastname(),
                request.getMobile(),
                business
        );
        return technicianRepository.save(technician);
    }

    private boolean isLocationVerified(Tag tag, ScanRequestDTO request){

        if (request.getLatitude() == null || request.getLongitude() == null) {
            return false;
        }
        if (request.getAccuracy() == null || request.getAccuracy() > maxAccuracyMeters) {
            return false;
        }
        double distance = geoDistanceCalculator.meters(
                tag.getLatitude(), tag.getLongitude(),
                request.getLatitude(), request.getLongitude()
        );
        return distance <= proximityMeters;
    }
}