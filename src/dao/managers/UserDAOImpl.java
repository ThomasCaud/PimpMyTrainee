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
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
	private static final String tableName = "Users";
	private static final String SQL_SELECTED_BY_NAME_OR_LASTNAME_OR_COMPANY = "SELECT * FROM Users WHERE firstname like ? or lastname like ? or company like ?";
	private static final String SQL_INSERT_USER = "INSERT INTO Users (firstname, lastname, email, password, company, phone, creationDate, isActive, role, managerId) VALUES (?,?,?,?,?,?,NOW(),?,?,?)";
	private static final String SQL_UPDATE_USER = "UPDATE Users set firstname = ?, lastname = ?, email = ?, company = ?, phone = ?, isActive = ?, role = ? WHERE id = ?";

	public UserDAOImpl() {
		super(null, tableName);
	}

	public UserDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected User map(ResultSet resultSet) throws SQLException {
		User user = new User();

		user.setId(resultSet.getInt("id"));
		user.setFirstname(resultSet.getString("firstname"));
		user.setLastname(resultSet.getString("lastname"));
		user.setEmail(resultSet.getString("email"));
		user.setPassword(resultSet.getString("password"));
		user.setCompany(resultSet.getString("company"));
		user.setPhone(resultSet.getString("phone"));
		user.setCreationDate(resultSet.getTimestamp("creationDate"));
		user.setIsActive(resultSet.getInt("isActive") == 1 ? true : false);
		user.setRole(E_Role.valueOf(resultSet.getString("role").toUpperCase()));

		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		if (user.getRole() == E_Role.TRAINEE) {
			User manager = userDAO.find(resultSet.getInt("managerId"));
			user.setManager(manager);
		}

		return user;
	}

	@Override
	public void createUser(User user, User creator) throws DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT_USER, true, user.getFirstname(),
					user.getLastname(), user.getEmail(), user.getPassword(), user.getCompany(), user.getPhone(),
					(user.getIsActive() ? 1 : 0), user.getRole().toString().toLowerCase(), creator.getId());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				user.setId(resultSet.getInt(1));
			} else {
				throw new DAOException(
						"Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public void updateUser(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_UPDATE_USER, false, user.getFirstname(),
					user.getLastname(), user.getEmail(), user.getCompany(), user.getPhone(), user.getIsActive(),
					user.getRole().toString().toLowerCase(), user.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}

	@Override
	public ArrayList<User> findUsersByNameOrLastnameOrCompany(String filter) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<User> users = new ArrayList<User>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_SELECTED_BY_NAME_OR_LASTNAME_OR_COMPANY, false,
					'%' + filter + '%', '%' + filter + '%', '%' + filter + '%');
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				User user = map(resultSet);
				users.add(user);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return users;
	}

	@Override
	public void updateIsActive(User user, boolean isActive) {
		this.update("isActive", isActive, "id", user.getId());
	}
}
