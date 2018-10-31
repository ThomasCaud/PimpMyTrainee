package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.User;

public interface UserDAO {
	
	void createUser(User user, User creator) throws DAOException;
	void updateUser(User user) throws DAOException;
	User findActiveUserByEmail(String email) throws DAOException;
	User findUserByEmail(String email) throws DAOException;
	User findUserByID(Integer id) throws DAOException;
	ArrayList<User> findAllUsers() throws DAOException;
	ArrayList<User> findUsersByNameOrLastnameOrCompany(String filter) throws DAOException;
	ArrayList<User> findAllUsers(Integer offset, Integer limit) throws DAOException;
	Integer countAllUsers() throws DAOException;
}
