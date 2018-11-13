package dao.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.UserDAO;
import models.beans.Answer;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.RecordAnswer;
import models.beans.User;

public class RecordDAOImpl extends AbstractDAOImpl<Record> implements RecordDAO {
	private static final String tableName = "records";

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

		RecordAnswerDAO recordAnswerDAO = DAOFactory.getInstance().getRecordAnswerDAO();
		AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
		ArrayList<RecordAnswer> recordAnswers = recordAnswerDAO.find("record", resultSet.getInt("id"));
		ArrayList<Answer> answers = new ArrayList<Answer>();
		for (RecordAnswer ra : recordAnswers) {
			answers.add(answerDAO.find(ra.getAnswerId()));
		}
		record.setAnswers(answers);

		return record;
	}
}
