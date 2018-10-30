package dao.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static dao.DAOCommon.*;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.QuizDAO;
import dao.interfaces.ThemeDAO;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.Theme;
import models.beans.User;

public class QuizDAOImpl implements QuizDAO {

	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Quizzes WHERE id = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Quizzes";
	private static final String SQL_SELECT_ALL_WITH_OFFSET_LIMIT = "SELECT * FROM Quizzes LIMIT ?,?";
	private static final String SQL_COUNT_ALL = "SELECT count(*) as count FROM Quizzes";
	private static final String SQL_INSERT_QUIZ = "INSERT INTO Quizzes (title, theme, creator, creationDate, isActive) VALUES (?,?,?,NOW(),?)";

	private static final String SQL_UPDATE_QUIZ = "UPDATE Quizzes set title = ?, theme = ?, isActive = ? WHERE id = ?";

	private DAOFactory daoFactory;

	public QuizDAOImpl() {
	}
	
	public QuizDAOImpl( DAOFactory daoFactory ) {
        	this.daoFactory = daoFactory;
    }
	
	private static Quiz map( ResultSet resultSet ) throws SQLException {
		Quiz quiz = new Quiz();

		quiz.setId( resultSet.getInt( "id" ) );
		quiz.setTitle( resultSet.getString("title") );
		quiz.setIsActive( resultSet.getInt( "isActive" ) == 1 ? true : false );
		quiz.setCreationDate( resultSet.getTimestamp( "creationDate" ) );
		
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		User user = userDAO.findUserByID( resultSet.getInt("creator") );
		quiz.setCreator(user);
		
		ThemeDAO themeDAO = DAOFactory.getInstance().getThemeDAO();
		Theme theme = themeDAO.findThemeByID( resultSet.getInt("theme") );
		quiz.setTheme(theme);
				
		return quiz;
	}

	public void createQuiz(Quiz quiz) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    
	    try {
			// Récupération d'une connexion depuis la Factory
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				SQL_INSERT_QUIZ,
				true,
				quiz.getTitle(),
				quiz.getTheme().getId(),
				quiz.getCreator().getId(),
				quiz.getIsActive()
			);
			int status = preparedStatement.executeUpdate();

			if( status == 0 ) {
				throw new DAOException( "Échec de la création du quiz, aucune ligne ajoutée dans la table." );
			}
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if ( resultSet.next() ) {
	            quiz.setId( resultSet.getInt( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création du quiz en base, aucun ID auto-généré retourné." );
	        }

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
	}

	public void updateQuiz(Quiz quiz) throws DAOException {
		 Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				SQL_UPDATE_QUIZ,
				false,
				quiz.getTitle(),
				quiz.getTheme().getId(),
				quiz.getIsActive(),
				quiz.getCreator().getId()
			);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( null, preparedStatement, connection );
		}
	}

	@Override
	public Quiz findQuizByID(Integer id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    Quiz quiz = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				quiz = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return quiz;
	}

	@Override
	public ArrayList<Quiz> findAllQuizzes() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_ALL, false);
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {
				Quiz quiz = map( resultSet );
				quizzes.add(quiz);
			}

		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return quizzes;
	}

	@Override
	public ArrayList<Quiz> findAllQuizzes(Integer offset, Integer limit) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_SELECT_ALL_WITH_OFFSET_LIMIT, false, offset, limit);
			resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				Quiz quiz = map( resultSet );
				quizzes.add(quiz);
			}
			
		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return quizzes;
	}

	@Override
	public Integer countAllQuizzes() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer result = 0;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement( connection, SQL_COUNT_ALL, false);
			resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				result = resultSet.getInt("count");
			}
			
		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
		
		return result;
	}
}
