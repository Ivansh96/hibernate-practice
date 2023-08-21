package com.ivansh.dao;

import com.ivansh.entity.Payment;

import org.hibernate.SessionFactory;


import javax.persistence.EntityManager;
import java.util.UUID;


public class PaymentRepository extends RepositoryBase<UUID, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(entityManager, Payment.class);
    }
}
