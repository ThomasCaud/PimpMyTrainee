package dao.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import dao.exceptions.DAOException;
import javafx.util.Pair;

public interface CommonDAO<T> {
	Integer count() throws DAOException;

	Integer count(String field, Object value) throws DAOException;

	T find(String field, Object value) throws DAOException;

	T find(Integer id);

	T findActive(String field, Object value) throws DAOException;

	ArrayList<T> findAll() throws DAOException;

	ArrayList<T> findAll(Integer offset, Integer limit) throws DAOException;

	ArrayList<T> findBy(String field, Object value) throws DAOException;

	ArrayList<T> findBy(String field, Object value, Integer offset, Integer limit) throws DAOException;

	ArrayList<T> findBy(HashMap<String, Object> filters) throws DAOException;

	ArrayList<T> find(HashMap<String, Object> filters, HashMap<String, Pair<String, String>> joinClauses)
			throws DAOException;

	void update(String fieldToUpdate, Object newValue, String fieldClause, Object clauseValue) throws DAOException;

	void delete(Integer id) throws DAOException;
}
