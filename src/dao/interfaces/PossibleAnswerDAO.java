package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.PossibleAnswer;

public interface PossibleAnswerDAO {
	void create(PossibleAnswer pa) throws DAOException;
	void update(PossibleAnswer pa) throws DAOException;
	PossibleAnswer findByID(Integer id) throws DAOException;
    ArrayList<PossibleAnswer> findByQuizId(int quizId) throws DAOException;
}
