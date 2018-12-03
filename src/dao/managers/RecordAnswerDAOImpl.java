package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.RecordAnswerDAO;
import models.beans.RecordAnswer;

public class RecordAnswerDAOImpl extends AbstractAssociativeDAOImpl<RecordAnswer> implements RecordAnswerDAO {
	private static final String tableName = "RecordAnswer";
	private static final String SQL_INSERT_RECORDANSWER = "INSERT INTO RecordAnswer (record,answer) VALUES (?,?)";

	public RecordAnswerDAOImpl() {
		super(null, tableName);
	}

	public RecordAnswerDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected RecordAnswer map(ResultSet resultSet) throws SQLException {
		RecordAnswer ra = new RecordAnswer();
		ra.setRecordId(resultSet.getInt("record"));
		ra.setAnswerId(resultSet.getInt("answer"));

		return ra;
	}

	@Override
	public void createRecordAnswer(RecordAnswer recordAnswer) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Récupération d'une connexion depuis la Factory
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT_RECORDANSWER, true,
					recordAnswer.getRecordId(), recordAnswer.getAnswerId());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création de record, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}
}
