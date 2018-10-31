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
import dao.interfaces.PossibleAnswerDAO;
import models.beans.PossibleAnswer;
import models.beans.Question;

public class PossibleAnswerDAOImpl extends AbstractDAOImpl<PossibleAnswer>  implements PossibleAnswerDAO {
	private static final String tableName = "possibleanswers";
    private static final String SQL_SELECT_BY_QUIZ_ID = "SELECT * from possibleanswers WHERE question = ?";
    private static final String SQL_INSERT = "INSERT INTO possibleanswers (label, isCorrect, isActive, position, question) VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE possibleanswers set label = ?, isCorrect = ?, isActive = ?, position = ?, question = ? WHERE id = ?";

	public PossibleAnswerDAOImpl() {
		super(null, tableName);
	}

	public PossibleAnswerDAOImpl( DAOFactory daoFactory ) {
		super(daoFactory, tableName);
    }

	protected PossibleAnswer map( ResultSet resultSet ) throws SQLException {
        PossibleAnswer pa = new PossibleAnswer();

        pa.setId( resultSet.getInt( "id" ) );
        pa.setLabel( resultSet.getString( "label" ) );
		pa.setIsCorrect( resultSet.getInt( "isCorrect" ) == 1 ? true : false );
		pa.setIsActive( resultSet.getInt( "isActive" ) == 1 ? true : false );
		pa.setPosition( resultSet.getInt( "position" ) );

        return pa;
	}

	public void create(Question qu, PossibleAnswer pa) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

	    try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				SQL_INSERT,
                true,
                pa.getLabel(),
                pa.getIsCorrect(),
                pa.getIsActive(),
                pa.getPosition(),
                qu.getId()
			);
			int status = preparedStatement.executeUpdate();

			if( status == 0 ) {
				throw new DAOException( "Échec de la création de la 'réponse possible', aucune ligne ajoutée dans la table." );
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if ( resultSet.next() ) {
	            pa.setId( resultSet.getInt( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de la 'réponse possible' en base, aucun ID auto-généré retourné." );
	        }

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}
	}

	public void update(Question qu, PossibleAnswer pa) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				SQL_UPDATE,
                false,
                pa.getLabel(),
                pa.getIsCorrect(),
                pa.getIsActive(),
                pa.getPosition(),
                qu.getId(),
                pa.getId()
			);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( null, preparedStatement, connection );
		}
	}

    @Override
    public ArrayList<PossibleAnswer> findByQuizId(int quizId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<PossibleAnswer> possibleAnswers = new ArrayList<PossibleAnswer>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
                connection,
                SQL_SELECT_BY_QUIZ_ID,
                false,
                quizId
            );
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {
				PossibleAnswer pa = map( resultSet );
				possibleAnswers.add(pa);
			}

		} catch (SQLException e) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return possibleAnswers;
    }
}
