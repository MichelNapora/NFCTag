package com.nfctag.building;

import com.nfctag.address.Address;
import com.nfctag.address.AddressRepository;
import com.nfctag.building.dto.BuildingAdminDto;
import com.nfctag.building.dto.CreateBuildingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final AddressRepository addressRepository;

    public BuildingService(BuildingRepository buildingRepository, AddressRepository addressRepository) {
        this.buildingRepository = buildingRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public List<BuildingAdminDto> listForAdmin() {
        return buildingRepository.findByArchivedIsNullOrderByName().stream()
                .map(BuildingService::toAdminDto)
                .toList();
    }

    @Transactional
    public BuildingAdminDto create(CreateBuildingRequest req) {
        Building b = new Building();
        b.setName(req.name().trim());
        b.setProjectCode(emptyToNull(req.projectCode()));
        b.setBuildingType(emptyToNull(req.buildingType()));

        // Adresse créée seulement si au moins une info est fournie.
        if (hasAddress(req)) {
            Address a = new Address();
            a.setStreet(emptyToNull(req.street()));
            a.setNumber(emptyToNull(req.number()));
            a.setPostalCode(emptyToNull(req.postalCode()));
            a.setCity(emptyToNull(req.city()));
            b.setAddress(addressRepository.save(a));
        }
        return toAdminDto(buildingRepository.save(b));
    }

    @Transactional
    public void archive(Long id) {
        Building b = buildingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bâtiment introuvable"));
        b.setArchived(OffsetDateTime.now());
        buildingRepository.save(b);
    }

    private static boolean hasAddress(CreateBuildingRequest req) {
        return notBlank(req.street()) || notBlank(req.number())
                || notBlank(req.postalCode()) || notBlank(req.city());
    }

    private static BuildingAdminDto toAdminDto(Building b) {
        String city = b.getAddress() != null ? b.getAddress().getCity() : null;
        return new BuildingAdminDto(b.getId(), b.getName(), b.getProjectCode(), b.getBuildingType(), city);
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
