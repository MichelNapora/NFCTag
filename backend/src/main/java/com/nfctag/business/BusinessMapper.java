package com.nfctag.business;

import com.nfctag.business.dto.BusinessAdminDto;
import com.nfctag.business.dto.BusinessDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public BusinessDto toDto(Business business) {
        return new BusinessDto(business.getId(), business.getName());
    }

    public BusinessAdminDto toAdminDto(Business business) {
        return new BusinessAdminDto(business.getId(), business.getName(), business.getBce());
    }
}
