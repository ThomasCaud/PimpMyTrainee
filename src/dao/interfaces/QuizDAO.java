package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Quiz;
import models.beans.User;

public interface QuizDAO extends CommonDAO<Quiz> {
	void createQuiz(Quiz quiz) throws DAOException;

	void updateQuiz(Quiz quiz) throws DAOException;

	void updateIsActive(Quiz quiz, boolean isActive);

	ArrayList<Quiz> findByManagerId(Integer managerId, Integer offset, Integer limit) throws DAOException;

	ArrayList<Quiz> searchQuizzes(Integer managerId, String value) throws DAOException;

	ArrayList<Quiz> searchAvailableQuizzes(User user, String value, Integer offset, Integer limit) throws DAOException;

	ArrayList<Quiz> searchAvailableQuizzes(User trainee, String value) throws DAOException;

	int countAvailableQuizzes(User trainee) throws DAOException;

	int countNbOfRecordsForAQuiz(Quiz quiz) throws DAOException;
}
