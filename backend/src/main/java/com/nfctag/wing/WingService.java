package com.nfctag.wing;

import com.nfctag.building.Building;
import com.nfctag.building.BuildingRepository;
import com.nfctag.wing.dto.CreateWingRequest;
import com.nfctag.wing.dto.WingAdminDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class WingService {

    private final WingRepository wingRepository;
    private final BuildingRepository buildingRepository;

    public WingService(WingRepository wingRepository, BuildingRepository buildingRepository) {
        this.wingRepository = wingRepository;
        this.buildingRepository = buildingRepository;
    }

    @Transactional(readOnly = true)
    public List<WingAdminDto> listForAdmin() {
        return wingRepository.findByArchivedIsNullOrderByName().stream()
                .map(WingService::toAdminDto)
                .toList();
    }

    @Transactional
    public WingAdminDto create(CreateWingRequest req) {
        Building building = buildingRepository.findById(req.buildingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bâtiment inconnu"));
        Wing w = new Wing();
        w.setBuilding(building);
        w.setName(req.name().trim());
        return toAdminDto(wingRepository.save(w));
    }

    @Transactional
    public void archive(Long id) {
        Wing w = wingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aile introuvable"));
        w.setArchived(OffsetDateTime.now());
        wingRepository.save(w);
    }

    private static WingAdminDto toAdminDto(Wing w) {
        Building b = w.getBuilding();
        return new WingAdminDto(w.getId(), w.getName(),
                b != null ? b.getId() : null,
                b != null ? b.getName() : null);
    }
}
