package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.AnswerDAO;
import models.beans.Answer;
import models.beans.Question;

public class AnswerDAOImpl extends AbstractDAOImpl<Answer> implements AnswerDAO {
	private static final String tableName = "answers";
	private static final String SQL_INSERT = "INSERT INTO " + tableName
			+ " (label, isCorrect, isActive, position, question) VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE " + tableName
			+ " set label = ?, isCorrect = ?, isActive = ?, position = ? WHERE id = ?";
	private static final String SQL_DISABLE_ANSWER = "UPDATE Answers set isActive = 0 WHERE id = ?";

	public AnswerDAOImpl() {
		super(null, tableName);
	}

	public AnswerDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected Answer map(ResultSet resultSet) throws SQLException {
		Answer pa = new Answer();

		pa.setId(resultSet.getInt("id"));
		pa.setLabel(resultSet.getString("label"));
		pa.setIsCorrect(resultSet.getInt("isCorrect") == 1 ? true : false);
		pa.setIsActive(resultSet.getInt("isActive") == 1 ? true : false);
		pa.setPosition(resultSet.getInt("position"));

		return pa;
	}

	public void create(Question qu, Answer pa) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT, true, pa.getLabel(), pa.getIsCorrect(),
					pa.getIsActive(), pa.getPosition(), qu.getId());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la 'réponse possible', aucune ligne ajoutée dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				pa.setId(resultSet.getInt(1));
			} else {
				throw new DAOException(
						"Échec de la création de la 'réponse possible' en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}

	public void update(Answer pa) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_UPDATE, false, pa.getLabel(), pa.getIsCorrect(),
					pa.getIsActive(), pa.getPosition(), pa.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}

	public void disable(Answer answer) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_DISABLE_ANSWER, false, answer.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}
}
