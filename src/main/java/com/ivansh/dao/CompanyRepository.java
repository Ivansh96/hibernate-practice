package com.ivansh.dao;

import com.ivansh.entity.Company;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.UUID;


public class CompanyRepository extends RepositoryBase<UUID, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}
