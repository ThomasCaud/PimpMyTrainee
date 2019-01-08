package models.beans;

import java.io.Serializable;

/**
 * This class does not exist in the data model It only exists in order to
 * centralize stats of the admin dashboard
 * 
 * @author Thomas
 *
 */
public class StatsAdminGlobal implements Serializable {
	private static final long serialVersionUID = 1L;

	private int nbActiveUser;
	private int nbInactiveUser;
	private int nbCreatedQuizzes;
	private int nbRecords;

	/**
	 * Default constructor
	 */
	public StatsAdminGlobal() {
		super();
		setNbActiveUser(0);
		setNbInactiveUser(0);
		setNbCreatedQuizzes(0);
		setNbRecords(0);
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param nbActiveUser
	 * @param nbInactiveUser
	 * @param nbCreatedQuizzes
	 * @param nbRecords
	 */
	public StatsAdminGlobal(int nbActiveUser, int nbInactiveUser, int nbCreatedQuizzes, int nbRecords) {
		super();
		this.nbActiveUser = nbActiveUser;
		this.nbInactiveUser = nbInactiveUser;
		this.nbCreatedQuizzes = nbCreatedQuizzes;
		this.nbRecords = nbRecords;
	}

	/**
	 * get nbActiveUser
	 * 
	 * @return nbActiveUser
	 */
	public int getNbActiveUser() {
		return nbActiveUser;
	}

	/**
	 * set nbActiveUser
	 * 
	 * @param nbActiveUser
	 *            of type int
	 */
	public void setNbActiveUser(int nbActiveUser) {
		this.nbActiveUser = nbActiveUser;
	}

	/**
	 * get nbInactiveUser
	 * 
	 * @return nbInactiveUser
	 */
	public int getNbInactiveUser() {
		return nbInactiveUser;
	}

	/**
	 * set nbInactiveUser
	 * 
	 * @param nbInactiveUser
	 *            of type int
	 */
	public void setNbInactiveUser(int nbInactiveUser) {
		this.nbInactiveUser = nbInactiveUser;
	}

	/**
	 * get nbCreatedQuizzes
	 * 
	 * @return nbCreatedQuizzes
	 */
	public int getNbCreatedQuizzes() {
		return nbCreatedQuizzes;
	}

	/**
	 * set nbCreatedQuizzes
	 * 
	 * @param nbCreatedQuizzes
	 *            of type int
	 */
	public void setNbCreatedQuizzes(int nbCreatedQuizzes) {
		this.nbCreatedQuizzes = nbCreatedQuizzes;
	}

	/**
	 * get nbRecords
	 * 
	 * @return nbRecords
	 */
	public int getNbRecords() {
		return nbRecords;
	}

	/**
	 * set nbRecords
	 * 
	 * @param nbRecords
	 *            of type int
	 */
	public void setNbRecords(int nbRecords) {
		this.nbRecords = nbRecords;
	}
}
