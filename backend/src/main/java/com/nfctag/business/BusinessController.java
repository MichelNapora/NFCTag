package com.nfctag.business;

import com.nfctag.business.dto.BusinessDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final BusinessService service;

    public BusinessController(BusinessService service) {
        this.service = service;
    }

    @GetMapping
    public List<BusinessDto> list() {
        return service.listActive();
    }
}
