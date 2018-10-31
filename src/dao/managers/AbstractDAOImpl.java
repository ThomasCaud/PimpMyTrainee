package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.CommonDAO;

public abstract class AbstractDAOImpl<T> implements CommonDAO<T> {
	protected DAOFactory daoFactory;
	private String tableName;
	
	public AbstractDAOImpl(DAOFactory daoFactory, String tableName) {
		this.daoFactory = daoFactory;
		this.tableName = tableName;
	}

	private String getSelectByIdQuery() {
		return "SELECT * FROM " + tableName + " WHERE id = ?";
	}
	
	protected abstract T map( ResultSet resultSet ) throws SQLException;

	@Override
	public T find(Integer id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    T t = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(
				connection,
				getSelectByIdQuery(),
				false,
				id
			);
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {
				t = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			silentClose( resultSet, preparedStatement, connection );
		}

		return t;
	}
}
