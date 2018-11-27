package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Record;
import models.beans.User;

public interface RecordDAO extends CommonDAO<Record> {
	ArrayList<Record> get(User trainee, String searchOnTitleQuiz);

	void createRecord(Record record) throws DAOException;
}
