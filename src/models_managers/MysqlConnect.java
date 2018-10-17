package models_managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnect {
	public static MysqlConnect db;
	public Connection conn;
	private Statement statement;
	
	private MysqlConnect() {
		String url = "jdbc:mysql://localhost:3306/";
        String dbName = "Pimpmytrainee";
        String timezone = "serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "Inaspfmoa";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName+"?"+timezone,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
	}
	
	/**
	 * @return MysqlConnect Database connection object
	 */
	public static synchronized MysqlConnect getDbCon() {
		if (db == null) {
			db = new MysqlConnect();
		}
		return db;
	}
	
	/**
	* @param query String The query to be executed
	* @return a ResultSet object containing the results or null if not available
	* @throws SQLException
	*/
	public ResultSet query(String query) throws SQLException{
		statement = db.conn.createStatement();
		ResultSet res = statement.executeQuery(query);
		return res;
	}

	/**
	* @param insertQuery String The Insert query
	* @return boolean
	* @throws SQLException
	*/
	public int insert(String insertQuery) throws SQLException {
		statement = db.conn.createStatement();
		int result = statement.executeUpdate(insertQuery);
		return result;
	}
	
	// Maxime, je le laisse ce main pour que tu puisses tester ta connexion à ta base
	// Une fois que tu as testé, je te laisse le supprimer
//	public static void main(String[] args) {
//		MysqlConnect db = MysqlConnect.getDbCon();
//		try {
//			db.query("select * from users");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
