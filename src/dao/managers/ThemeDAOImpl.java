package dao.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static dao.DAOCommon.*;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.ThemeDAO;
import models.beans.Theme;

public class ThemeDAOImpl implements ThemeDAO {

	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Themes WHERE id = ?";

	private DAOFactory daoFactory;

	public ThemeDAOImpl() {
	}
	
	public ThemeDAOImpl( DAOFactory daoFactory ) {
        	this.daoFactory = daoFactory;
    }
	
	private static Theme map( ResultSet resultSet ) throws SQLException {
		Theme theme = new Theme();

		Theme.setId( resultSet.getInt( "id" ) );
		Theme.setLabel( resultSet.getLabel( "label" ) );
		
		return theme;
	}

	@Override
	public Theme findThemeByID(Integer id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    Theme theme = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {
				theme = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return theme;
	}
}
