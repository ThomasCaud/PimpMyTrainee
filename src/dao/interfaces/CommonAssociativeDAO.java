package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;

public interface CommonAssociativeDAO<T> {
    T find(String fieldA, Integer idA, String fieldB, Integer idB) throws DAOException;

    ArrayList<T> find(String field, Integer value) throws DAOException;

    void delete(String fieldA, Integer idA, String fieldB, Integer idB) throws DAOException;
}
