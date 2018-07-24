package com.videumcorp.spring_boot_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videumcorp.spring_boot_example.repository.CompanyRepository;
import com.videumcorp.spring_boot_example.dto.Company;
import com.videumcorp.spring_boot_example.utils.MyStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
public class CompanyController {

    private final
    CompanyRepository companyRepository;

    @Autowired
    private CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    //Can have multiple paths
    //Check application.properties root path
    //In this project: http://localhost:8080/api/v1/companies/google or http://localhost:8080/api/v1/other_path/google
    //Two companies: google and apple
    @RequestMapping(value = {"companies/{company_name}", "other_path/{company_name}"})
    public Company company(@PathVariable String company_name) {
        return getCompany(company_name);
    }

    private Company getCompany(String company_name) {
        String url = MyStrings.KEY_BASE_URL + company_name + ".json";
        ObjectMapper mapper = new ObjectMapper();
        Company company;
        try {
            company = mapper.readValue(new URL(url), Company.class);
            System.out.println("Read company: " + company.getName());
            //Enable to save a company to a MongoDB
            //saveCompany(company);
            return company;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveCompany(Company company) {
        companyRepository.save(company);
        System.out.println("Saved company: " + companyRepository.findCompanyByName(company.getName()).getName());
    }
}