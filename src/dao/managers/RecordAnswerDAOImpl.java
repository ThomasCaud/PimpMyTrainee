package dao.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.interfaces.RecordAnswerDAO;
import models.beans.RecordAnswer;

public class RecordAnswerDAOImpl extends AbstractAssociativeDAOImpl<RecordAnswer> implements RecordAnswerDAO {
	private static final String tableName = "RecordAnswer";

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
}
