package com.zarbafian.company.service;

import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> findAll();

    Owner findOne(Long id);

    Owner create(Owner owner);

    Owner update(Owner owner);

    void delete(Long id);

    List<Owner> getCompanyOwners(Company company);
}
