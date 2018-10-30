package dao.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static dao.DAOCommon.*;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.QuestionDAO;
import dao.interfaces.QuizDAO;
import models.beans.Question;

public class QuestionDAOImpl implements QuestionDAO {

	private static final String SQL_SELECT_BY_ID = "SELECT * FROM questions WHERE id = ?";

	private DAOFactory daoFactory;

	public QuestionDAOImpl() {
	}

	public QuestionDAOImpl( DAOFactory daoFactory ) {
        	this.daoFactory = daoFactory;
    }

	private static Question map( ResultSet resultSet ) throws SQLException {
		Question question = new Question();

		question.setId( resultSet.getInt( "id" ) );
        question.setLabel( resultSet.getString( "label" ) );
        question.setIsActive( resultSet.getInt( "isActive" ) == 1 ? true : false );
        question.setPosition( resultSet.getInt( "position" ));

        // todo add quiz attribute in Question ?
        // QuizDAO quizDAO = DAOFactory.getInstance().getQuizDAO();
        // Quiz quiz = quizDAO.findQuizByID( resultSet.getInt( "quiz" ));
        // question.setQuiz(quiz);

		return question;
	}

	@Override
	public Question findByID(Integer id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    Question question = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_BY_ID, false, id );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {
				question = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return question;
	}
}
