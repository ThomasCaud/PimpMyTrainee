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
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.UserDAO;
import javafx.util.Pair;
import models.beans.Answer;
import models.beans.Quiz;
import models.beans.Ranking;
import models.beans.Record;
import models.beans.RecordAnswer;
import models.beans.User;

public class RecordDAOImpl extends AbstractDAOImpl<Record> implements RecordDAO {
	private static final String tableName = "records";

	private static final String SQL_SELECT_AS_ADMIN = "SELECT * FROM records\r\n"
			+ "JOIN quizzes ON records.quiz = quizzes.id\r\n" + "JOIN (\r\n" + "	-- get number of respondent\r\n"
			+ "	select quiz, count(distinct trainee) as nbRespondents from records\r\n"
			+ "	join recordanswer on records.id = recordanswer.record\r\n" + "	group by quiz\r\n"
			+ ") nbRespondentByQuiz on nbRespondentByQuiz.quiz = records.quiz\r\n" + "JOIN (\r\n" + "	-- get rank\r\n"
			+ "	select trainee, score,\r\n" + "	RANK() OVER(ORDER BY score desc) scoreRank\r\n" + "	from records\r\n"
			+ ") scoreRank on scoreRank.trainee = records.trainee\r\n" + "JOIN (\r\n"
			+ "	-- get top score and its duration\r\n"
			+ "	select quiz, score as bestScore, duration as durationOfBestScore\r\n" + "    from records\r\n"
			+ "    order by score desc\r\n" + "    limit 1\r\n"
			+ ") bestResponses on bestResponses.quiz = records.quiz\r\n"
			+ "WHERE records.trainee like ? AND title like ? AND isActive like 1;";

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

		if (AbstractDAOImpl.hasColumn(resultSet, "nbRespondents")) {
			// Si cette colonne existe, alors cela signifie que l'on a executé
			// la requête permettant d'obtenir les informations sur le
			// classement
			Ranking ranking = new Ranking(resultSet.getInt("nbRespondents"), resultSet.getInt("scoreRank"),
					resultSet.getInt("bestScore"), resultSet.getInt("durationOfBestScore"));
			ranking.toString();
			record.setRanking(ranking);
		}

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
	public ArrayList<Record> getOnAdminView(User trainee, String searchOnTitleQuiz) {
		searchOnTitleQuiz = (searchOnTitleQuiz != null ? '%' + searchOnTitleQuiz + '%' : "%%");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Record> records = new ArrayList<Record>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_SELECT_AS_ADMIN, false, trainee.getId(),
					searchOnTitleQuiz);
			resultSet = preparedStatement.executeQuery();

			System.out.println("Execute as admin V");

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
