package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.ThemeDAO;
import models.beans.Theme;

public class ThemeDAOImpl extends AbstractDAOImpl<Theme> implements ThemeDAO {
	private static final String tableName = "Themes";
	private static final String SQL_INSERT_THEME = "INSERT INTO " + tableName + " (label) VALUES (?)";

	public ThemeDAOImpl() {
		super(null, tableName);
	}

	public ThemeDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected Theme map(ResultSet resultSet) throws SQLException {
		Theme theme = new Theme();

		theme.setId(resultSet.getInt("id"));
		theme.setLabel(resultSet.getString("label"));

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
			preparedStatement = initPreparedStatement(connection, SQL_INSERT_THEME, true, theme.getLabel());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création du thème, aucune ligne ajoutée dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				theme.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Échec de la création du thème en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}
}
