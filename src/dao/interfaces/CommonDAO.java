package dao.interfaces;

import dao.exceptions.DAOException;

public interface CommonDAO<T> {
	T find(Integer id) throws DAOException;
}
