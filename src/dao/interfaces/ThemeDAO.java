package dao.interfaces;

import java.util.ArrayList;

import dao.exceptions.DAOException;
import models.beans.Theme;

public interface ThemeDAO extends CommonDAO<Theme> {
	void createTheme(Theme theme) throws DAOException;
}
