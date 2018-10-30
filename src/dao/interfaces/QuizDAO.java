package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Quiz;

public interface QuizDAO {
	void createQuiz(Quiz Quiz) throws DAOException;
	void updateQuiz(Quiz Quiz) throws DAOException;
	Quiz findQuizByID(Integer id) throws DAOException;
	ArrayList<Quiz> findAllQuizzes() throws DAOException;
	ArrayList<Quiz> findAllQuizzes(Integer offset, Integer limit) throws DAOException;
	Integer countAllQuizzes() throws DAOException;
}
