package com.videumcorp.spring_boot_example.repository;

import com.videumcorp.spring_boot_example.dto.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, Long> {
    Company findCompanyByName(String name);
}