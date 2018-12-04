package models.beans;

import java.io.Serializable;

public class StatsAdminGlobal implements Serializable {
	private static final long serialVersionUID = 1L;

	private int nbActiveUser;
	private int nbInactiveUser;
	private int nbCreatedQuizzes;
	private int nbRecords;

	public StatsAdminGlobal(int nbActiveUser, int nbInactiveUser, int nbCreatedQuizzes, int nbRecords) {
		super();
		this.nbActiveUser = nbActiveUser;
		this.nbInactiveUser = nbInactiveUser;
		this.nbCreatedQuizzes = nbCreatedQuizzes;
		this.nbRecords = nbRecords;
	}

	public StatsAdminGlobal() {
		super();
		setNbActiveUser(0);
		setNbInactiveUser(0);
		setNbCreatedQuizzes(0);
		setNbRecords(0);
	}

	public int getNbActiveUser() {
		return nbActiveUser;
	}

	public void setNbActiveUser(int nbActiveUser) {
		this.nbActiveUser = nbActiveUser;
	}

	public int getNbInactiveUser() {
		return nbInactiveUser;
	}

	public void setNbInactiveUser(int nbInactiveUser) {
		this.nbInactiveUser = nbInactiveUser;
	}

	public int getNbCreatedQuizzes() {
		return nbCreatedQuizzes;
	}

	public void setNbCreatedQuizzes(int nbCreatedQuizzes) {
		this.nbCreatedQuizzes = nbCreatedQuizzes;
	}

	public int getNbRecords() {
		return nbRecords;
	}

	public void setNbRecords(int nbRecords) {
		this.nbRecords = nbRecords;
	}
}
