package dao.interfaces;

import dao.exceptions.DAOException;

public interface CommonDAO<T> {
	T find(Integer id) throws DAOException;
	void delete(Integer id) throws DAOException;
}
