package com.ivansh.dao;

import com.ivansh.entity.User;

import javax.persistence.EntityManager;
import java.util.UUID;

public class UserRepository extends RepositoryBase<UUID, User> {

    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
