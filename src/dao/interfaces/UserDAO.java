package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.User;

public interface UserDAO {
	
	void createUser(User user) throws DAOException;
	User findActiveUserByEmail(String email) throws DAOException;
	ArrayList<User> findAllUsers() throws DAOException;

}
