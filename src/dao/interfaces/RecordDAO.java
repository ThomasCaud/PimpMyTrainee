package dao.interfaces;

import java.util.ArrayList;

import models.beans.Record;
import models.beans.User;

public interface RecordDAO extends CommonDAO<Record> {
	ArrayList<Record> get(User trainee, String searchOnTitleQuiz);
}
