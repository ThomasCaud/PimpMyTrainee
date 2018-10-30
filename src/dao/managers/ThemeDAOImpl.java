package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.ThemeDAO;
import models.beans.Theme;

public class ThemeDAOImpl implements ThemeDAO {

	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Themes WHERE id = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Themes";

	private DAOFactory daoFactory;

	public ThemeDAOImpl() {
	}
	
	public ThemeDAOImpl( DAOFactory daoFactory ) {
        	this.daoFactory = daoFactory;
    }
	
	private static Theme map( ResultSet resultSet ) throws SQLException {
		Theme theme = new Theme();

		theme.setId( resultSet.getInt( "id" ) );
		theme.setLabel( resultSet.getString( "label" ) );
		
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

	@Override
	public ArrayList<Theme> findAllThemes() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Theme> themes = new ArrayList<Theme>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_ALL, false);
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {
				Theme theme = map( resultSet );
				themes.add(theme);
			}

		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return themes;
	}
}
