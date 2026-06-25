package com.nfctag.business;

import com.nfctag.business.dto.BusinessDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BusinessService {

    private final BusinessRepository repository;
    private final BusinessMapper mapper;

    public BusinessService(BusinessRepository repository, BusinessMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /** Sociétés actives, triées par nom (liste déroulante). */
    public List<BusinessDto> listActive() {
        return repository.findByArchivedIsNullOrderByName().stream()
                .map(mapper::toDto)
                .toList();
    }
}
