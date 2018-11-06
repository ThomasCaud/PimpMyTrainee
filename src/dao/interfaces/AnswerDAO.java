package dao.interfaces;

import java.sql.ResultSet;

import dao.exceptions.DAOException;
import models.beans.Answer;
import models.beans.Question;

public interface AnswerDAO extends CommonDAO<Answer> {
    void create(Question qu, Answer pa) throws DAOException;

    void update(Question qu, Answer pa) throws DAOException;

    Answer getAnswerFromResultSet(ResultSet res);
}
