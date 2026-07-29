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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ScanResult scan(UUID scanToken, ScanCommand command){

        Tag tag = tagRepository.findByScanToken(scanToken).orElseThrow(() -> new TagNotFoundException("Tag not found"));

        Technician technician = resolveTechnician(command);

        final LocationCheck location = checkLocation(tag, command);

        Optional<Presence> open = presenceRepository.findByTechnicianIdAndTagIdAndDepartedAtIsNull(technician.getId(), tag.getId());

        Presence presence;
        ScanAction action;

        if (open.isPresent()) {
            presence = open.get();
            presence.setDepartedAt(OffsetDateTime.now());
            action = ScanAction.DEPARTURE;
        } else {
            presence = new Presence(technician, tag, OffsetDateTime.now(),
                    location.getStatus(), location.getDistanceMeters());
            action = ScanAction.ARRIVAL;
        }

        presenceRepository.save(presence);

        return new ScanResult(technician, tag, presence, location, action);
    }

    private Technician resolveTechnician(ScanCommand command){
        if (command.deviceToken() != null) {
            return technicianRepository.findByDeviceToken(command.deviceToken())
                    .orElseThrow(() -> new TechnicianNotFoundException("Token not found"));
        }

        if (command.mobile() != null) {
            Optional<Technician> existing = technicianRepository.findByMobile(command.mobile());
            if (existing.isPresent()) {
                Technician technician = existing.get();
                updateBusinessIfChanged(technician, command.businessId());
                return technicianRepository.save(technician);
            }
        }

        if (isBlank(command.firstname()) || isBlank(command.lastname())
                || isBlank(command.mobile()) || command.businessId() == null) {
            throw new InvalidScanException("The first scan needs firstname, lastname, mobile and business");
        }

        Business business = businessRepository.findById(command.businessId())
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        Technician technician = new Technician(
                command.firstname(),
                command.lastname(),
                command.mobile(),
                business
        );
        return technicianRepository.save(technician);
    }

    private boolean isBlank(String value){
        return value == null || value.isBlank();
    }

    private LocationCheck checkLocation(Tag tag, ScanCommand command){

        if (tag.getLatitude() == null || tag.getLongitude() == null) {
            return new LocationCheck(LocationStatus.TAG_NOT_CALIBRATED, null);
        }
        if (command.latitude() == null || command.longitude() == null) {
            return new LocationCheck(LocationStatus.NO_GPS, null);
        }
        if (command.accuracy() == null || command.accuracy() > maxAccuracyMeters) {
            return new LocationCheck(LocationStatus.IMPRECISE, null);
        }

        double distance = geoDistanceCalculator.meters(
                tag.getLatitude(), tag.getLongitude(),
                command.latitude(), command.longitude()
        );

        return new LocationCheck(
                distance <= proximityMeters ? LocationStatus.VERIFIED : LocationStatus.TOO_FAR,
                distance
        );
    }
    
    private void updateBusinessIfChanged(Technician technician, UUID businessId){
        if (businessId == null || businessId.equals(technician.getBusiness().getId())) {
            return;
        }

        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found"));

        technician.setBusiness(business);
    }
}