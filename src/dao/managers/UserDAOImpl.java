package dao.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static dao.DAOCommon.*;

import dao.DAOCommon;
import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.UserDAO;
import models.beans.User;

public class UserDAOImpl implements UserDAO {
	
	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM Users WHERE email = ?";


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
		user.setPassword( resultSet.getString( "password" ) );
		user.setCompany( resultSet.getString( "company" ) );
		user.setPhone( resultSet.getString( "phone" ) );
		user.setCreationDate( resultSet.getTimestamp( "creationDate" ) );
		user.setIsActive( resultSet.getBoolean( "isActive" ) );
		
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

}
