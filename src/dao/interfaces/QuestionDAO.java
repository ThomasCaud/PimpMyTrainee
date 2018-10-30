package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.Question;
import models.beans.Quiz;

public interface QuestionDAO {
	void create(Quiz quiz, Question question) throws DAOException;
    Question findByID(Integer id) throws DAOException;
}
