package dao.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.interfaces.AnswerDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import models.beans.Answer;
import models.beans.Record;
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

	RecordDAO recordDAO = DAOFactory.getInstance().getRecordDAO();
	Record record = recordDAO.find(resultSet.getInt("record"));
	ra.setRecord(record);

	AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
	Answer answer = answerDAO.find(resultSet.getInt("answer"));
	ra.setAnswer(answer);

	return ra;
    }
}
