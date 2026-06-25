package com.nfctag.scan;

import com.nfctag.business.Business;
import com.nfctag.business.BusinessMapper;
import com.nfctag.business.BusinessRepository;
import com.nfctag.nfc.Nfc;
import com.nfctag.nfc.NfcRepository;
import com.nfctag.presence.Presence;
import com.nfctag.presence.PresenceRepository;
import com.nfctag.scan.dto.*;
import com.nfctag.wing.Wing;
import com.nfctag.worker.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScanService {

    private final NfcRepository nfcRepository;
    private final WorkerRepository workerRepository;
    private final WorkerDeviceRepository deviceRepository;
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final PresenceRepository presenceRepository;

    public ScanService(NfcRepository nfcRepository,
                       WorkerRepository workerRepository,
                       WorkerDeviceRepository deviceRepository,
                       BusinessRepository businessRepository,
                       BusinessMapper businessMapper,
                       PresenceRepository presenceRepository) {
        this.nfcRepository = nfcRepository;
        this.workerRepository = workerRepository;
        this.deviceRepository = deviceRepository;
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
        this.presenceRepository = presenceRepository;
    }

    /**
     * Scan initial. Si un jeton appareil valide est fourni, on traite le scan.
     * Sinon on renvoie NEED_IDENTIFICATION pour demander le mobile.
     */
    @Transactional
    public ScanResult scan(ScanRequest req) {
        Nfc nfc = requireTag(req.tagToken());

        Optional<WorkerDevice> device = parseUuid(req.deviceToken())
                .flatMap(deviceRepository::findByToken);

        if (device.isPresent()) {
            WorkerDevice d = device.get();
            d.setLastSeen(OffsetDateTime.now());
            return process(d.getWorker(), nfc, null);
        }

        return ScanResult.needIdentification(toTagInfo(nfc));
    }

    /**
     * Repli n°1 : identification par mobile. Si le technicien existe déjà,
     * on traite le scan et on lui redonne un jeton appareil (sans redemander
     * la société). Sinon on renvoie NEED_BUSINESS.
     */
    @Transactional
    public ScanResult lookup(LookupRequest req) {
        Nfc nfc = requireTag(req.tagToken());

        Optional<Worker> worker = workerRepository.findByMobile(req.mobile().trim());
        if (worker.isPresent()) {
            String newToken = issueDevice(worker.get());
            return process(worker.get(), nfc, newToken);
        }

        // Mobile inconnu → demander la société (premier passage)
        return ScanResult.needBusiness(toTagInfo(nfc),
                businessRepository.findByArchivedIsNullOrderByName().stream()
                        .map(businessMapper::toDto)
                        .toList());
    }

    /**
     * Repli n°2 : premier passage. On crée le technicien rattaché à sa société,
     * on émet un jeton appareil et on traite le scan.
     */
    @Transactional
    public ScanResult register(RegisterRequest req) {
        Nfc nfc = requireTag(req.tagToken());
        String mobile = req.mobile().trim();

        // Idempotence : si le mobile a été créé entre-temps, on le réutilise.
        Worker worker = workerRepository.findByMobile(mobile).orElseGet(() -> {
            Business business = businessRepository.findById(req.businessId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Société inconnue"));
            Worker w = new Worker();
            w.setBusiness(business);
            w.setMobile(mobile);
            w.setFirstname(emptyToNull(req.firstname()));
            w.setLastname(emptyToNull(req.lastname()));
            return workerRepository.save(w);
        });

        String newToken = issueDevice(worker);
        return process(worker, nfc, newToken);
    }

    // ---- Logique de bascule arrivée / départ ----

    private ScanResult process(Worker worker, Nfc nfc, String newDeviceToken) {
        OffsetDateTime now = OffsetDateTime.now();

        Optional<Presence> open = presenceRepository
                .findFirstByWorkerIdAndNfcIdAndDepartedAtIsNull(worker.getId(), nfc.getId());

        String action;
        if (open.isPresent()) {
            Presence p = open.get();
            p.setDepartedAt(now);
            p.setEstimated(false);
            p.setUpdated(now);
            presenceRepository.save(p);
            action = ScanResult.ACTION_DEPARTURE;
        } else {
            Presence p = new Presence();
            p.setWorker(worker);
            p.setNfc(nfc);
            p.setArrivedAt(now);
            presenceRepository.save(p);
            action = ScanResult.ACTION_ARRIVAL;
        }

        return ScanResult.recognized(action, newDeviceToken, toTagInfo(nfc),
                worker.displayName(), worker.getBusiness().getName(), now);
    }

    // ---- Helpers ----

    private String issueDevice(Worker worker) {
        WorkerDevice device = new WorkerDevice();
        device.setWorker(worker);
        device.setToken(UUID.randomUUID());
        device.setLastSeen(OffsetDateTime.now());
        deviceRepository.save(device);
        return device.getToken().toString();
    }

    private Nfc requireTag(String tagToken) {
        return parseUuid(tagToken)
                .flatMap(nfcRepository::findByScanToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag inconnu"));
    }

    private TagInfo toTagInfo(Nfc nfc) {
        Wing wing = nfc.getWing();
        String wingName = wing != null ? wing.getName() : null;
        String buildingName = wing != null && wing.getBuilding() != null ? wing.getBuilding().getName() : null;
        return new TagInfo(nfc.getName(), buildingName, wingName);
    }

    private static Optional<UUID> parseUuid(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(UUID.fromString(value.trim()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
