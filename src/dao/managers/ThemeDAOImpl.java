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
import models.beans.User;

public class ThemeDAOImpl implements ThemeDAO {

	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Themes WHERE id = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Themes";
	private static final String SQL_SELECT_PAR_LABEL = "SELECT * FROM Themes WHERE label = ?";
	private static final String SQL_INSERT_THEME = "INSERT INTO Themes (label) VALUES (?)";

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
	
	@Override
	public Theme findThemeByLabel(String label) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    Theme theme = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_PAR_LABEL, false, label );
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
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
	public void createTheme(Theme theme) throws DAOException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    
	    try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_INSERT_THEME, true, theme.getLabel() );
			int status = preparedStatement.executeUpdate();
			
			if( status == 0 ) {
				throw new DAOException( "Échec de la création du thème, aucune ligne ajoutée dans la table." );
			}
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if ( resultSet.next() ) {
				theme.setId( resultSet.getInt( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création du thème en base, aucun ID auto-généré retourné." );
	        }

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
	}
}
