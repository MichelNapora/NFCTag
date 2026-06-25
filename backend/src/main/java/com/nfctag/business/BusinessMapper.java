package com.nfctag.business;

import com.nfctag.business.dto.BusinessDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public BusinessDto toDto(Business business) {
        return new BusinessDto(business.getId(), business.getName());
    }
}
