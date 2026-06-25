package com.nfctag.business;

import com.nfctag.business.dto.BusinessAdminDto;
import com.nfctag.business.dto.BusinessDto;
import com.nfctag.business.dto.CreateBusinessRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BusinessService {

    private final BusinessRepository repository;
    private final BusinessMapper mapper;

    public BusinessService(BusinessRepository repository, BusinessMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /** Sociétés actives, triées par nom (liste déroulante du scan). */
    @Transactional(readOnly = true)
    public List<BusinessDto> listActive() {
        return repository.findByArchivedIsNullOrderByName().stream()
                .map(mapper::toDto)
                .toList();
    }

    /** Sociétés actives, vue administration. */
    @Transactional(readOnly = true)
    public List<BusinessAdminDto> listForAdmin() {
        return repository.findByArchivedIsNullOrderByName().stream()
                .map(mapper::toAdminDto)
                .toList();
    }

    @Transactional
    public BusinessAdminDto create(CreateBusinessRequest req) {
        Business b = new Business();
        b.setName(req.name().trim());
        b.setBce(emptyToNull(req.bce()));
        return mapper.toAdminDto(repository.save(b));
    }

    /** Archivage (suppression logique) : la société disparaît des listes. */
    @Transactional
    public void archive(Long id) {
        Business b = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Société introuvable"));
        b.setArchived(OffsetDateTime.now());
        repository.save(b);
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
