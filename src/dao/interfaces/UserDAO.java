package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.User;

public interface UserDAO extends CommonDAO<User> {
	void createUser(User user, User creator) throws DAOException;

	void updateUser(User user) throws DAOException;

	ArrayList<User> findUsersByNameOrLastnameOrCompany(int adminId, String filter) throws DAOException;

	void updateIsActive(User user, boolean isActive);
}