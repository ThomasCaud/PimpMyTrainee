package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Quizz;

public interface QuizzDAO {
	void createQuizz(Quizz Quizz) throws DAOException;
	void updateQuizz(Quizz Quizz) throws DAOException;
	Quizz findQuizzByID(Integer id) throws DAOException;
	ArrayList<Quizz> findAllQuizzs() throws DAOException;
	ArrayList<Quizz> findAllQuizzs(Integer offset, Integer limit) throws DAOException;
	Integer countAllQuizzs() throws DAOException;
}
