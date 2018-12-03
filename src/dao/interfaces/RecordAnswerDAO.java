package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.RecordAnswer;

public interface RecordAnswerDAO extends CommonAssociativeDAO<RecordAnswer> {
	void createRecordAnswer(RecordAnswer recordAnswer) throws DAOException;
}
