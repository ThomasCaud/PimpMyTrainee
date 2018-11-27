package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.CommonDAO;
import javafx.util.Pair;

public abstract class AbstractDAOImpl<T> implements CommonDAO<T> {
	protected abstract T map(ResultSet resultSet) throws SQLException;

	protected DAOFactory daoFactory;
	private String tableName;

	public AbstractDAOImpl(DAOFactory daoFactory, String tableName) {
		this.daoFactory = daoFactory;
		this.tableName = tableName;
	}

	private String getCountQuery() {
		return "SELECT count(*) as count FROM " + tableName;
	}

	private String getCountWithFilterQuery(String field) {
		return "SELECT count(*) as count FROM " + tableName + " where " + field + " = ?";
	}

	private String getSelectAllWithOffsetQuery() {
		return "SELECT * FROM " + tableName + " LIMIT ?,?";
	}

	private String getSelectQueryWithOffsetQuery(String field) {
		return "SELECT * FROM " + tableName + " WHERE " + field + " = ? LIMIT ?,?";
	}

	private String getUpdateQuery(String fieldToUpdate, String fieldClause) {
		return "UPDATE " + tableName + " SET " + fieldToUpdate + " = ? WHERE " + fieldClause + " = ?";
	}

	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param filters
	 * @param joinClauses
	 * @return query with WHERE clauses depending on filters, and with JOIN
	 *         depending on joinClauses parameter
	 */
	private String getSelectQuery(HashMap<String, Object> filters, HashMap<String, Pair<String, String>> joinClauses) {
		String query = "SELECT * FROM " + tableName;

		for (Entry<String, Pair<String, String>> joinClause : joinClauses.entrySet()) {
			String tableName = joinClause.getKey();
			Pair<String, String> join = joinClause.getValue();

			query += " JOIN " + tableName + " ON " + join.getKey() + " = " + join.getValue();
		}

		boolean isFirstClause = true;
		for (Entry<String, Object> entry : filters.entrySet()) {
			String field = entry.getKey();
			if (isFirstClause) {
				query += " WHERE " + field + " like ? ";
				isFirstClause = false;
			} else {
				query += " AND " + field + " like ? ";
			}
		}

		return query;
	}

	private String getDeletedByIdQuery() {
		return "DELETE FROM " + tableName + " WHERE id = ?";
	}

	@Override
	public Integer count() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer result = 0;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getCountQuery(), false);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return result;
	}

	@Override
	public Integer count(String field, Object value) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer result = 0;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getCountWithFilterQuery(field), false, value);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return result;
	}

	@Override
	public T find(String field, Object value) {
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put(field, value);
		ArrayList<T> results = findBy(filters);

		return results.get(0);
	}

	@Override
	public T find(Integer id) {
		return find("id", id);
	}

	@Override
	public T findActive(String field, Object value) {
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put(field, value);
		filters.put("isActive", 1);
		ArrayList<T> results = findBy(filters);

		return results == null || results.size() == 0 ? null : results.get(0);
	}

	@Override
	public ArrayList<T> findAll() throws DAOException {
		HashMap<String, Object> filters = new HashMap<String, Object>();
		ArrayList<T> results = findBy(filters);

		return results;
	}

	@Override
	public ArrayList<T> findAll(Integer offset, Integer limit) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<T> tList = new ArrayList<T>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getSelectAllWithOffsetQuery(), false, offset, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				T t = map(resultSet);
				tList.add(t);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return tList;
	}

	@Override
	public ArrayList<T> findBy(String field, Object value) throws DAOException {
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put(field, value);
		ArrayList<T> results = findBy(filters);

		return results;
	}

	@Override
	public ArrayList<T> findBy(String field, Object value, Integer offset, Integer limit) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<T> tList = new ArrayList<T>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getSelectQueryWithOffsetQuery(field), false, value,
					offset, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				T pa = map(resultSet);
				tList.add(pa);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return tList;
	}

	public void delete(Integer id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getDeletedByIdQuery(), true, id);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.rowDeleted()) {
				throw new DAOException("Echec de la suppression [[table:" + tableName + "],[id:" + id + "]]");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public ArrayList<T> find(HashMap<String, Object> filters, HashMap<String, Pair<String, String>> joinClauses)
			throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<T> tList = new ArrayList<T>();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getSelectQuery(filters, joinClauses), false,
					filters.values().toArray());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				T pa = map(resultSet);
				tList.add(pa);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
		return tList;
	}

	@Override
	public ArrayList<T> findBy(HashMap<String, Object> filters) throws DAOException {
		HashMap<String, Pair<String, String>> joinClauses = new HashMap<String, Pair<String, String>>();
		return find(filters, joinClauses);
	}

	@Override
	public void update(String fieldToUpdate, Object newValue, String fieldClause, Object clauseValue)
			throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, getUpdateQuery(fieldToUpdate, fieldClause), false,
					newValue, clauseValue);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}
}
