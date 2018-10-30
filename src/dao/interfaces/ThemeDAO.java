package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Theme;
import models.beans.User;

public interface ThemeDAO {
	Theme findThemeByID(Integer id) throws DAOException;
	ArrayList<Theme> findAllThemes() throws DAOException;
	Theme findThemeByLabel(String label) throws DAOException;
	void createTheme(Theme theme) throws DAOException;
}
