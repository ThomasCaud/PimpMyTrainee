package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.PossibleAnswer;
import models.beans.Question;

public interface PossibleAnswerDAO {
	void create(Question qu, PossibleAnswer pa) throws DAOException;
	void update(Question qu, PossibleAnswer pa) throws DAOException;
	PossibleAnswer findByID(Integer id) throws DAOException;
    ArrayList<PossibleAnswer> findByQuizId(int quizId) throws DAOException;
}
