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
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.UserDAO;
import models.beans.Answer;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.User;

public class RecordDAOImpl extends AbstractDAOImpl<Record> implements RecordDAO {
    private static final String tableName = "records";
    private static final String SQL_SELECT_ANSWERS_BY_RECORDID = "select * from recordanswer join answers on recordanswer.answer = answers.id where answer = ?;";

    public RecordDAOImpl() {
	super(null, tableName);
    }

    public RecordDAOImpl(DAOFactory daoFactory) {
	super(daoFactory, tableName);
    }

    protected Record map(ResultSet resultSet) throws SQLException {
	Record record = new Record();

	record.setId(resultSet.getInt("id"));
	record.setScore(resultSet.getInt("score"));
	record.setDuration(resultSet.getInt("duration"));

	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	User user = userDAO.find(resultSet.getInt("trainee"));
	record.setTrainee(user);

	QuizDAO quizDAO = DAOFactory.getInstance().getQuizDAO();
	Quiz quiz = quizDAO.find(resultSet.getInt("quiz"));
	record.setQuiz(quiz);

	record.setAnswers(getGivenAnswers(resultSet.getInt("id")));

	return record;
    }

    private ArrayList<Answer> getGivenAnswers(int recordId) throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ArrayList<Answer> answers = new ArrayList<Answer>();

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, SQL_SELECT_ANSWERS_BY_RECORDID, true, recordId);
	    resultSet = preparedStatement.executeQuery();

	    while (resultSet.next()) {
		AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
		Answer answer = answerDAO.getAnswerFromResultSet(resultSet);
		answers.add(answer);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    silentClose(resultSet, preparedStatement, connection);
	}
	return answers;
    }
}
