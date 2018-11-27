package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.UserDAO;
import javafx.util.Pair;
import models.beans.Answer;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.RecordAnswer;
import models.beans.User;

public class RecordDAOImpl extends AbstractDAOImpl<Record> implements RecordDAO {
	private static final String tableName = "records";
	private static final String SQL_INSERT_RECORD = "INSERT INTO Records (score, duration, quiz, trainee) VALUES (?,?,?,?)";

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
		record.setContextId(UUID.fromString(resultSet.getString("contextId")));

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
	 * Retourne la liste des enregistrements actifs d'un utilisateur
	 */
	public ArrayList<Record> get(User trainee, String searchOnTitleQuiz) {
		if (null == searchOnTitleQuiz)
			searchOnTitleQuiz = "%%";
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("trainee", trainee.getId());
		filters.put("title", searchOnTitleQuiz);
		filters.put("isActive", 1);

		HashMap<String, Pair<String, String>> joinClauses = new HashMap<String, Pair<String, String>>();
		Pair<String, String> join = new Pair<String, String>("records.quiz", "quizzes.id");
		joinClauses.put("quizzes", join);

		return find(filters, joinClauses);
	}

	@Override
	public void createRecord(Record record) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Récupération d'une connexion depuis la Factory
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT_RECORD, true, record.getScore(),
					record.getDuration(), record.getQuiz().getId(), record.getTrainee().getId());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création de record, aucune ligne ajoutée dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				record.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Échec de la création de record, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}
}
