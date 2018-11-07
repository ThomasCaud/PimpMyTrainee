package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.Answer;
import models.beans.Question;

public interface AnswerDAO extends CommonDAO<Answer> {
    void create(Question qu, Answer pa) throws DAOException;

    void update(Question qu, Answer pa) throws DAOException;

    void disable(Answer answer);
}
