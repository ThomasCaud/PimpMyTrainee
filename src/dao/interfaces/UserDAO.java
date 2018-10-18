package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.User;

public interface UserDAO {
	
	void createUser(User user) throws DAOException;
	User findUserByEmail(String email) throws DAOException;

}
