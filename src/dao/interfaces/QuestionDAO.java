package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.Question;
import models.beans.Quiz;

public interface QuestionDAO extends CommonDAO<Question> {
	void create(Quiz quiz, Question question) throws DAOException;
}
