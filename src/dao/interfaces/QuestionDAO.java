package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Question;

public interface QuestionDAO {
    // todo add create
    Question findByID(Integer id) throws DAOException;
}
