package org.example;


import java.util.List;

public interface GenericDao<T, ID> {

    void save(T entity);

    T findById(ID id);

    List<T> findAll();

    T update(T entity);

    boolean deleteById(ID id);
}