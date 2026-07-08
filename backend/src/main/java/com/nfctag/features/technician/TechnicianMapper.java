package com.nfctag.features.technician;

import com.nfctag.features.business.Business;
import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.business.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {
    @Autowired
    private BusinessRepository businessRepository;

    public TechnicianDTO toDto (Technician w){
        return new TechnicianDTO(
                w.getId(),
                w.getFirstname(),
                w.getLastname(),
                w.getMobile(),
                w.getBusiness().getId()
        );
    }

    public Technician toEntity(TechnicianDTO dto){
        Business business= this.businessRepository.findById(dto.getBusinessId()).orElseThrow(()-> new BusinessNotFoundException("Business not found : "+dto.getBusinessId()));
        return new Technician(dto.getFirstname(),dto.getLastname(),dto.getMobile(),business);
    }
}
