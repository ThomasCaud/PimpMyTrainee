package dao.interfaces;

import dao.exceptions.DAOException;
import models.beans.PossibleAnswer;
import models.beans.Question;

public interface PossibleAnswerDAO extends CommonDAO<PossibleAnswer> {
	void create(Question qu, PossibleAnswer pa) throws DAOException;
	void update(Question qu, PossibleAnswer pa) throws DAOException;
}
