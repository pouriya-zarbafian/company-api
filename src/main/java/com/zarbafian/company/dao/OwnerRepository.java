package com.zarbafian.company.dao;

import com.zarbafian.company.model.Company;
import com.zarbafian.company.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query(
            value = "SELECT * FROM company_owner co JOIN owner o ON o.id = co.owner_id WHERE co.company_id = ?1",
            nativeQuery = true
    )
    List<Owner> getCompanyOwners(Company company);
}
