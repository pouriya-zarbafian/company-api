package com.zarbafian.company.api;

import static org.junit.Assert.*;

import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listCompanies() {

        ResponseEntity<? extends ArrayList> responseEntity =
                restTemplate.getForEntity("/api/companies", new ArrayList<Company>().getClass());

        List<Company> companies = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(companies.size(), 2);
    }


    @Test
    public void createCompany() {

        Company company = createCompanyData("Test Company");

        ResponseEntity<Company> responseEntity =
                restTemplate.postForEntity("/api/companies", company, Company.class);

        Company createdCompany = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(company.getName(), createdCompany.getName());
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
