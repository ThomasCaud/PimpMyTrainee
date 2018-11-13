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
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.UserDAO;
import models.beans.Answer;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.RecordAnswer;
import models.beans.User;

public class RecordDAOImpl extends AbstractDAOImpl<Record> implements RecordDAO {
	private static final String SQL_SELECT_FOR_MANAGER = "select * from records join users on records.trainee = users.id where managerId = ?;";
	private static final String SQL_SELECT_FOR_TRAINEE = "select * from records where trainee = ?";
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

	/**
	 * Retourne la liste des enregistrements visibles, selon les droits de
	 * l'utilisateur passé en paramètre
	 */
	public ArrayList<Record> get(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Record> records = new ArrayList<Record>();

		try {
			connection = daoFactory.getConnection();
			final String query = user.getRole() == E_Role.ADMIN ? SQL_SELECT_FOR_MANAGER : SQL_SELECT_FOR_TRAINEE;
			preparedStatement = initPreparedStatement(connection, query, false, user.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Record record = map(resultSet);
				records.add(record);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return records;
	}
}
