package dao.interfaces;

import models.beans.StatsAdminGlobal;
import models.beans.User;

public interface StatsDAO {
	StatsAdminGlobal get(User admin);
}
