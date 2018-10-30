package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Quiz;

public interface QuizDAO {
	void createQuiz(Quiz quiz) throws DAOException;
	void updateQuiz(Quiz quiz) throws DAOException;
	Quiz findQuizByID(Integer id) throws DAOException;
	ArrayList<Quiz> findAllQuizzes() throws DAOException;
    ArrayList<Quiz> findAllQuizzes(Integer offset, Integer limit) throws DAOException;
    ArrayList<Quiz> findQuizzesByTitleOrTheme(String filter) throws DAOException;
	Integer countAllQuizzes() throws DAOException;
}
