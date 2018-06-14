package com.zarbafian.company.service;

import com.zarbafian.company.dao.CompanyRepository;
import com.zarbafian.company.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceBean implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceBean.class);

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {

        LOGGER.debug(">> findAll");

        List<Company> companies = companyRepository.findAll();

        LOGGER.debug("<< findAll");

        return companies;
    }

    public Company findOne(Long id) {

        LOGGER.debug(">> findOne : {}", id);

        Company company = null;

        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()) {
            company = optionalCompany.get();
        }

        LOGGER.debug("<< findOne : {}", company==null?"null":company);

        return company;
    }

    public Company create(Company company){

        LOGGER.debug(">> create : {}", company);

        Company createdCompany = companyRepository.save(company);

        LOGGER.debug("<< create : {}", createdCompany);

        return createdCompany;
    }

    public Company update(Company company){

        LOGGER.debug(">> update : {}", company);

        Company updatedCompany = companyRepository.save(company);

        LOGGER.debug("<< update : {}", updatedCompany);

        return updatedCompany;
    }

    public void delete(Long id) {

        LOGGER.debug(">> delete : {}", id);

        companyRepository.deleteById(id);

        LOGGER.debug("<< delete : {}", id);
    }
}
