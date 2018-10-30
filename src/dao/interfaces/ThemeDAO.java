package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Theme;

public interface ThemeDAO {
	Theme findThemeByID(Integer id) throws DAOException;
}
