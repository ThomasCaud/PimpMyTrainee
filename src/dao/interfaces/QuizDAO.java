package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Quiz;

public interface QuizDAO extends CommonDAO<Quiz> {
    void createQuiz(Quiz quiz) throws DAOException;

    void updateQuiz(Quiz quiz) throws DAOException;

    ArrayList<Quiz> findByManagerId(Integer managerId, Integer offset, Integer limit) throws DAOException;

    ArrayList<Quiz> searchQuizzes(String value) throws DAOException;

    ArrayList<Quiz> searchQuizzes(Integer managerId, String value) throws DAOException;
}
