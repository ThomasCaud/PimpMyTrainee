package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.PossibleAnswerDAO;
import dao.interfaces.QuestionDAO;
import models.beans.Question;
import models.beans.Quiz;

public class QuestionDAOImpl implements QuestionDAO {

	private static final String SQL_INSERT = "INSERT INTO Question (label, isActive, position, quiz) VALUES (?,?,?,?)";
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
        
        PossibleAnswerDAO paDAO = DAOFactory.getInstance().getPossibleAnswerDAO();
        question.setPossibleAnswers(paDAO.findByQuizId( resultSet.getInt( "id" )));

		return question;
	}
	
	@Override
	public void create(Quiz quiz, Question question) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

	    try {
	    	connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				SQL_INSERT,
				true,
				question.getLabel(),
				question.getIsActive(),
				question.getPosition(),
				quiz.getId()
			);

			int status = preparedStatement.executeUpdate();

			if( status == 0 ) {
				throw new DAOException( "Echec de la creation de la question, aucune ligne ajoutee dans la table." );
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if ( resultSet.next() ) {
	            quiz.setId( resultSet.getInt( 1 ) );
	        } else {
	            throw new DAOException( "Echec de la creation de la question en base, aucun ID auto-genere retourne." );
	        }

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
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
