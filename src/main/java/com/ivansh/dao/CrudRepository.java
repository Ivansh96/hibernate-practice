package com.ivansh.dao;

import com.ivansh.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<K extends Serializable, E extends BaseEntity> {

   E save(E entity);

   void delete(K id);

   void update(E entity);

   Optional<E> findById(K id);

   List<E> findAll();
}
