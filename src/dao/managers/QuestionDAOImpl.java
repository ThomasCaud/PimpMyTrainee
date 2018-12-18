package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuestionDAO;
import models.beans.Answer;
import models.beans.Question;
import models.beans.Quiz;

public class QuestionDAOImpl extends AbstractDAOImpl<Question> implements QuestionDAO {
	private static final String tableName = "Questions";
	private static final String SQL_INSERT = "INSERT INTO Questions (label, isActive, position, quiz) VALUES (?,?,?,?)";
	private static final String SQL_UPDATE_QUESTION = "UPDATE Questions set label = ?, isActive = ?, position = ? WHERE id = ?";
	private static final String SQL_DISABLE_QUESTION = "UPDATE Questions set isActive = 0 WHERE id = ?";

	public QuestionDAOImpl() {
		super(null, tableName);
	}

	public QuestionDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected Question map(ResultSet resultSet) throws SQLException {
		Question question = new Question();

		question.setId(resultSet.getInt("id"));
		question.setLabel(resultSet.getString("label"));
		question.setIsActive(resultSet.getInt("isActive") == 1 ? true : false);
		question.setPosition(resultSet.getInt("position"));

		AnswerDAO paDAO = DAOFactory.getInstance().getAnswerDAO();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("question", resultSet.getInt("id"));
		filters.put("isActive", 1);
		ArrayList<Answer> answers = paDAO.findBy(filters);
		question.setPossibleAnswers(answers);

		for (Answer answer : answers) {
			if (answer.getIsCorrect())
				question.setCorrectAnswer(answer);
		}
		return question;
	}

	@Override
	public void create(Quiz quiz, Question question) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT, true, question.getLabel(),
					question.getIsActive(), question.getPosition(), quiz.getId());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Echec de la creation de la question, aucune ligne ajoutee dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				question.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Echec de la creation de la question en base, aucun ID auto-genere retourne.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}

	public void updateQuestion(Question question) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_UPDATE_QUESTION, false, question.getLabel(),
					question.getIsActive(), question.getPosition(), question.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}

	public void disable(Question question) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_DISABLE_QUESTION, false, question.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}
}
