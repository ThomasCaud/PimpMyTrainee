package dao.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static dao.DAOCommon.*;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

public class UserDAOImpl implements UserDAO {
	
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM Users WHERE email = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Users";

	private DAOFactory daoFactory;

	public UserDAOImpl() {
	}
	
	public UserDAOImpl( DAOFactory daoFactory ) {
        	this.daoFactory = daoFactory;
    	}
	
	private static User map( ResultSet resultSet ) throws SQLException {
		User user = new User();

		user.setId( resultSet.getInt( "id" ) );
		user.setFirstname( resultSet.getString( "firstname" ) );
		user.setLastname( resultSet.getString( "lastname" ) );
		user.setEmail( resultSet.getString( "email" ) );
		user.setPassword( resultSet.getString( "password" ) );
		user.setCompany( resultSet.getString( "company" ) );
		user.setPhone( resultSet.getString( "phone" ) );
		user.setCreationDate( resultSet.getTimestamp( "creationDate" ) );
		user.setIsActive( resultSet.getBoolean( "isActive" ) );
		user.setRole( E_Role.valueOf(resultSet.getString( "role" ).toUpperCase() ) );
		
		return user;
	}

	@Override
	public void createUser(User user) throws DAOException {
	}

	@Override
	public User findUserByEmail(String email) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    User user = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_PAR_EMAIL, false, email );
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				user = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return user;
	}

	@Override
	public ArrayList<User> findAllUsers() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_ALL, false);
			resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				User user = map( resultSet );
				users.add(user);
			}
			
		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
		
		return users;
	}
}
