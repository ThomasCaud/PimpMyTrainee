package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.CommonDAO;

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

    private String getSelectQuery(String field) {
	return "SELECT * FROM " + tableName + " WHERE " + field + "  = ?";
    }

    private String getSelectQuery(HashMap<String, Object> filters) {
	String query = "SELECT * FROM " + tableName;
	boolean isFirstClause = true;

	for (Entry<String, Object> entry : filters.entrySet()) {
	    String field = entry.getKey();
	    if (isFirstClause) {
		query += " WHERE " + field + " = ? ";
		isFirstClause = false;
	    } else {
		query += " AND " + field + " = ? ";
	    }
	}

	return query;
    }

    private String getSelectActiveFilterQuery(String field) {
	return "SELECT * FROM " + tableName + " WHERE " + field + " = ? AND isActive = 1";
    }

    private String getSelectAllQuery() {
	return "SELECT * FROM " + tableName;
    }

    private String getSelectAllWithOffsetQuery() {
	return "SELECT * FROM " + tableName + " LIMIT ?,?";
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
    public T find(String field, Object value) throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	T t = null;

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectQuery(field), false, value);
	    resultSet = preparedStatement.executeQuery();

	    if (resultSet.next()) {
		t = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    silentClose(resultSet, preparedStatement, connection);
	}

	return t;
    }

    @Override
    public T find(Integer id) {
	return find("id", id);
    }

    @Override
    public T findActive(String field, Object value) throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	T t = null;

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectActiveFilterQuery(field), false, value);
	    resultSet = preparedStatement.executeQuery();

	    if (resultSet.next()) {
		t = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    silentClose(resultSet, preparedStatement, connection);
	}

	return t;
    }

    @Override
    public ArrayList<T> findAll() throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ArrayList<T> tList = new ArrayList<T>();

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectAllQuery(), false);
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
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ArrayList<T> tList = new ArrayList<T>();

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectQuery(field), false, value);
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
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ArrayList<T> tList = new ArrayList<T>();

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectQuery(filters), false,
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
}
