package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.StatsDAO;
import models.beans.StatsAdminGlobal;
import models.beans.User;

public class StatsDAOImpl implements StatsDAO {
	private static final String SQL_GET_STATS_ADMIN_GLOBAL = "select * from ("
			+ "	select count(*) as nbactiveuser from users"
			+ "	where role = 'trainee' and managerId = ? and isActive = 1" + ") nbActiveUser" + " join ("
			+ "	select count(*) as nbinactiveuser from users"
			+ "	where role = 'trainee' and managerId = ? and isActive = 0" + ") nbInactiveUser" + " join ("
			+ "	select count(*) as nbcreatedquizzes from quizzes where creator = ?" + ") nbCreatedQuizzes" + " join ("
			+ "	select count(*) as nbrecords from records" + " join users on users.id = records.trainee"
			+ " where managerId = ?" + ") nbRecords;";

	protected DAOFactory daoFactory;

	public StatsDAOImpl(DAOFactory dao) {
		daoFactory = dao;
	}

	StatsAdminGlobal mapStatsAdminGlobal(ResultSet resultSet) throws SQLException {
		StatsAdminGlobal stats = new StatsAdminGlobal();

		stats.setNbActiveUser(resultSet.getInt("nbactiveuser"));
		stats.setNbInactiveUser(resultSet.getInt("nbinactiveuser"));
		stats.setNbCreatedQuizzes(resultSet.getInt("nbcreatedquizzes"));
		stats.setNbRecords(resultSet.getInt("nbrecords"));

		return stats;
	}

	@Override
	public StatsAdminGlobal get(User admin) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		StatsAdminGlobal stats = new StatsAdminGlobal();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_GET_STATS_ADMIN_GLOBAL, false, admin.getId(),
					admin.getId(), admin.getId(), admin.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				stats = mapStatsAdminGlobal(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
		return stats;
	}
}
