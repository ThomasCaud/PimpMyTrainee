package dao.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import dao.exceptions.DAOException;

public interface CommonDAO<T> {
    Integer count() throws DAOException;

    T find(String field, Object value) throws DAOException;

    T find(Integer id);

    T findActive(String field, Object value) throws DAOException;

    ArrayList<T> findAll() throws DAOException;

    ArrayList<T> findAll(Integer offset, Integer limit) throws DAOException;

    ArrayList<T> findBy(String field, Object value) throws DAOException;

    ArrayList<T> findBy(HashMap<String, Object> filters) throws DAOException;

    void delete(Integer id) throws DAOException;
}
