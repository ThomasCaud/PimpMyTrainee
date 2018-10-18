package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCommon {

	/* Fermeture silencieuse du resultset */
	public static void silentClose( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
	        }
	    }
	}

	/* Fermeture silencieuse du statement */
	public static void silentClose( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
	        }
	    }
	}

	/* Fermeture silencieuse de la connexion */
	public static void silentClose( Connection connection ) {
	    if ( connection != null ) {
	        try {
	        	connection.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
	        }
	    }
	}

	/* Fermetures silencieuses du statement et de la connexion */
	public static void silentClose( Statement statement, Connection connection ) {
		silentClose( statement );
		silentClose( connection );
	}

	/* Fermetures silencieuses du resultset, du statement et de la connexion */
	public static void silentClose( ResultSet resultSet, Statement statement, Connection connection ) {
		silentClose( resultSet );
		silentClose( statement );
		silentClose( connection );
	}
	
	public static PreparedStatement initPreparedStatement( Connection connection, String sql, boolean returnGeneratedKeys, Object... objects ) throws SQLException {
	    PreparedStatement preparedStatement = connection.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objects.length; i++ ) {
	        preparedStatement.setObject( i + 1, objects[i] );
	    }
	    return preparedStatement;
	}

}
