package com.zarbafian.company.api;

import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;
import com.zarbafian.company.service.CompanyService;
import com.zarbafian.company.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(
            value = "/api/companies"
    )
    public ResponseEntity<List<Company>> listAllCompanies(){

        LOGGER.debug(">> listAllCompanies");

        List<Company> companies = companyService.findAll();

        LOGGER.debug("<< listAllCompanies");

        return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/companies",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {

        LOGGER.debug(">> createCompany");

        // id should be null
        if(company.getId() != null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        // check owners
        if(!isDataValid(company)) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        Company createdEntity = companyService.create(company);

        LOGGER.debug("<< createCompany");

        return new ResponseEntity<Company>(createdEntity, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/companies/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable(value = "id") Long id) {

        LOGGER.debug(">> updateCompany");

        // id cannot be null
        if(id == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        Company existingCompany = companyService.findOne(id);

        // check that specified company exist
        if(existingCompany == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        // check owners
        if(!isDataValid(company)) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        company.setId(id);

        Company updatedCompany = companyService.update(company);

        LOGGER.debug("<< updateCompany");

        return new ResponseEntity<Company>(updatedCompany, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/companies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Company> findCompanyById(@PathVariable(value = "id") Long id) {

        LOGGER.debug(">> findCompanyById");

        // id cannot be null
        if(id == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        Company company = companyService.findOne(id);

        if(company == null) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }

        LOGGER.debug("<< findCompanyById");

        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/companies/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteCompanyById(@PathVariable(value = "id") Long id) {

        LOGGER.debug(">> deleteCompanyById");

        // id cannot be null
        if (id == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        Company existingEntity = companyService.findOne(id);

        // target company must exist
        if (existingEntity == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        companyService.delete(id);

        LOGGER.debug("<< deleteCompanyById");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "/api/companies/{id}/owners",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Company> addOwnerToCompany(@PathVariable(value = "id") Long companyId, @RequestBody Owner owner) {

        LOGGER.debug(">> addOwnerToCompany");

        // id should not be null
        if(companyId == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        // owner id should not be null
        if(owner.getId() == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        Owner ownerEntity = ownerService.findOne(owner.getId());

        // check owner exists
        if(ownerEntity == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        Company companyEntity = companyService.findOne(companyId);

        // check company exists
        if(companyEntity == null) {
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        }

        companyEntity.getOwners().add(ownerEntity);

        Company updatedEntity = companyService.update(companyEntity);

        LOGGER.debug("<< addOwnerToCompany");

        return new ResponseEntity<Company>(updatedEntity, HttpStatus.CREATED);
    }

    private boolean isDataValid(Company company) {

        if(isNullOrEmpty(company.getName()) || isNullOrEmpty(company.getAddress()) || isNullOrEmpty(company.getCountry()) || isNullOrEmpty(company.getCity())) {
            return false;
        }

        // at least one beneficial owner is required
        if(company.getOwners().size() < 1) {
            return false;
        }

        // check that the owners exists
        for(Owner owner: company.getOwners()) {
            Owner existingEntity = ownerService.findOne(owner.getId());
            if(existingEntity == null) {
                return false;
            }
        }

        return true;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
