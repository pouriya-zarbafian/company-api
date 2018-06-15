package com.zarbafian.company.api;

import static org.junit.Assert.*;

import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * List all companies and verify list is not empty.
     */
    @Test
    public void listCompanies() {

        ResponseEntity<? extends ArrayList> responseEntity =
                restTemplate.getForEntity("/api/companies", new ArrayList<Company>().getClass());

        List<Company> companies = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(companies.size() > 0);
    }

    /**
     * Retrieve an existing company.
     */
    @Test
    public void findExistingCompany() {

        ResponseEntity<Company> responseEntity =
                restTemplate.getForEntity("/api/companies/1", Company.class);

        Company company = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(company.getId(), null);
    }

    /**
     * Retrieve a non-existing company.
     */
    @Test
    public void findNonExistingCompany() {

        ResponseEntity<Company> responseEntity =
                restTemplate.getForEntity("/api/companies/-1", Company.class);

        Company company = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(company, null);
    }

    /**
     * Try to create a company without all the mandatory data.
     */
    @Test
    public void createCompanyWithMissingData() {

        Company company = createCompanyData("Test Company");
        company.setAddress(null);

        ResponseEntity<Company> responseEntity =
                restTemplate.postForEntity("/api/companies", company, Company.class);

        Company createdCompany = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Create a company with valid data;
     */
    @Test
    public void createCompanyWithValidData() {

        Company company = createCompanyData("Test Company");

        ResponseEntity<Company> responseEntity =
                restTemplate.postForEntity("/api/companies", company, Company.class);

        Company createdCompany = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(company.getName(), createdCompany.getName());
        assertEquals(company.getAddress(), createdCompany.getAddress());
        assertEquals(company.getCity(), createdCompany.getCity());
        assertEquals(company.getCountry(), createdCompany.getCountry());
        assertEquals(company.getPhoneNumber(), createdCompany.getPhoneNumber());
        assertEquals(company.getEmail(), createdCompany.getEmail());
        // TODO: check owners
    }

    /**
     * Update an existing company providing valid data.
     */
    @Test
    public void updateCompanyWithValidData() {

        ResponseEntity<Company> companyEntity =
                restTemplate.getForEntity("/api/companies/1", Company.class);

        Company company = companyEntity.getBody();

        String newName = company.getName() + " [updated]";

        // update the company name
        company.setName(newName);

        ResponseEntity<Company> responseEntityUpdate =
                restTemplate.exchange("/api/companies/" + company.getId(), HttpMethod.PUT, new HttpEntity<>(company), Company.class);

        Company updatedCompany = responseEntityUpdate.getBody();

        assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());
        assertEquals(company.getName(), newName);
        assertEquals(company.getName(), updatedCompany.getName());
        assertEquals(company.getAddress(), updatedCompany.getAddress());
        assertEquals(company.getCity(), updatedCompany.getCity());
        assertEquals(company.getCountry(), updatedCompany.getCountry());
        assertEquals(company.getPhoneNumber(), updatedCompany.getPhoneNumber());
        assertEquals(company.getEmail(), updatedCompany.getEmail());
        // TODO: check owners
    }

    /**
     * Try to update an existing company with invalid data.
     */
    @Test
    public void updateCompanyWithInvalidData() {

        ResponseEntity<Company> companyEntity =
                restTemplate.getForEntity("/api/companies/1", Company.class);

        Company company = companyEntity.getBody();

        String newName = company.getName() + " [updated]";
        company.setName(newName);

        // remove all beneficial owners
        company.getOwners().clear();

        ResponseEntity<Company> responseEntityUpdate =
                restTemplate.exchange("/api/companies/" + company.getId(), HttpMethod.PUT, new HttpEntity<>(company), Company.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntityUpdate.getStatusCode());
    }

    /**
     * Delete an existing company.
     */
    @Test
    public void deleteExistingCompany() {

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/companies/2", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    /**
     * Try to delete a non-existing company.
     */
    @Test
    public void deleteNonExistingCompany() {

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/companies/-1", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add an existing owner to a company.
     */
    @Test
    public void addExistingOwnerToCompany() {

        ResponseEntity<Company> responseEntity = restTemplate.getForEntity("/api/companies/1", Company.class);

        Company company = responseEntity.getBody();

        int ownersInitCount = company.getOwners().size();

        ResponseEntity<Company> updatedCompanyEntity = restTemplate.postForEntity("/api/companies/1/owners", new Owner(4L), Company.class);

        Company updatedCompany = updatedCompanyEntity.getBody();

        assertEquals(HttpStatus.CREATED, updatedCompanyEntity.getStatusCode());
        assertEquals(updatedCompany.getOwners().size(), ownersInitCount + 1);
    }

    /**
     * Add a non-existing owner to a company.
     */
    @Test
    public void addNonExistingOwnerToCompany() {

        ResponseEntity<Company> responseEntity = restTemplate.getForEntity("/api/companies/1", Company.class);

        Company company = responseEntity.getBody();

        ResponseEntity<Company> updatedCompanyEntity = restTemplate.postForEntity("/api/companies/1/owners", new Owner(99L), Company.class);

        Company updatedCompany = updatedCompanyEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, updatedCompanyEntity.getStatusCode());
        assertEquals(updatedCompany, null);
    }

    private Company createCompanyData(String name) {

        Company company = new Company();
        company.setName(name);
        company.setAddress("12 Three Street");
        company.setCity("Paris");
        company.setCountry("France");
        company.setOwners(Arrays.asList(new Owner(1L)));

        return company;
    }
}
