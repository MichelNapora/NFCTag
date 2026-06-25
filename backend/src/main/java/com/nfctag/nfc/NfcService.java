package com.nfctag.nfc;

import com.nfctag.config.NfctagProperties;
import com.nfctag.nfc.dto.CreateTagRequest;
import com.nfctag.nfc.dto.TagAdminDto;
import com.nfctag.wing.Wing;
import com.nfctag.wing.WingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NfcService {

    private final NfcRepository nfcRepository;
    private final WingRepository wingRepository;
    private final NfctagProperties properties;

    public NfcService(NfcRepository nfcRepository, WingRepository wingRepository, NfctagProperties properties) {
        this.nfcRepository = nfcRepository;
        this.wingRepository = wingRepository;
        this.properties = properties;
    }

    @Transactional(readOnly = true)
    public List<TagAdminDto> listForAdmin() {
        return nfcRepository.findByArchivedIsNullOrderByName().stream()
                .map(this::toAdminDto)
                .toList();
    }

    @Transactional
    public TagAdminDto create(CreateTagRequest req) {
        Wing wing = wingRepository.findById(req.wingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aile inconnue"));

        // Règle métier : un seul tag (actif) par aile.
        if (nfcRepository.existsByWingIdAndArchivedIsNull(wing.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cette aile a déjà un tag.");
        }

        Nfc nfc = new Nfc();
        nfc.setWing(wing);
        nfc.setName(req.name().trim());
        nfc.setScanToken(UUID.randomUUID());
        return toAdminDto(nfcRepository.save(nfc));
    }

    @Transactional
    public void archive(Long id) {
        Nfc nfc = nfcRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag introuvable"));
        nfc.setArchived(OffsetDateTime.now());
        nfcRepository.save(nfc);
    }

    private TagAdminDto toAdminDto(Nfc nfc) {
        Wing wing = nfc.getWing();
        String wingName = wing != null ? wing.getName() : null;
        String buildingName = (wing != null && wing.getBuilding() != null) ? wing.getBuilding().getName() : null;
        String token = nfc.getScanToken().toString();
        // URL à encoder sur le tag physique.
        String url = properties.getPublicBaseUrl() + "/scan/" + token;
        return new TagAdminDto(nfc.getId(), nfc.getName(),
                wing != null ? wing.getId() : null, wingName, buildingName, token, url);
    }
}
