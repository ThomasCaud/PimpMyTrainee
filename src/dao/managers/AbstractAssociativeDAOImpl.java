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
import dao.interfaces.CommonAssociativeDAO;

public abstract class AbstractAssociativeDAOImpl<T> implements CommonAssociativeDAO<T> {
    protected abstract T map(ResultSet resultSet) throws SQLException;

    protected DAOFactory daoFactory;
    private String tableName;

    public AbstractAssociativeDAOImpl(DAOFactory daoFactory, String tableName) {
	this.daoFactory = daoFactory;
	this.tableName = tableName;
    }

    private String getSelectCompositeQuery(String fieldA, String fieldB) {
	return "SELECT * FROM " + tableName + " WHERE " + fieldA + " = ? and " + fieldB + " = ?";
    }

    private String getSelectByFieldQuery(String fieldName) {
	return "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
    }

    private String getDeletedQuery(String fieldA, String fieldB) {
	return "DELETE from " + tableName + " where " + fieldA + " = ? and " + fieldB + " = ?";
    }

    @Override
    public T find(String fieldA, Integer idA, String fieldB, Integer idB) throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	T t = null;

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectCompositeQuery(fieldA, fieldB), false, idA,
		    idB);
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
    public ArrayList<T> find(String field, Integer value) throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	ArrayList<T> tList = new ArrayList<T>();

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getSelectByFieldQuery(field), false, value);
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
    public void delete(String fieldA, Integer idA, String fieldB, Integer idB) throws DAOException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
	    connection = daoFactory.getConnection();
	    preparedStatement = initPreparedStatement(connection, getDeletedQuery(fieldA, fieldB), true, idA, idB);
	    resultSet = preparedStatement.executeQuery();

	    if (!resultSet.rowDeleted()) {
		throw new DAOException("Echec de la suppression de la réponse [[" + fieldA + ": " + idA + "],[" + fieldB
			+ ": " + idB + "]]");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    silentClose(resultSet, preparedStatement, connection);
	}
    }
}
