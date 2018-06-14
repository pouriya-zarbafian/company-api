package com.zarbafian.company.service;

import com.zarbafian.company.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company findOne(Long id);

    Company create(Company company);

    Company update(Company company);

    void delete(Long id);
}
