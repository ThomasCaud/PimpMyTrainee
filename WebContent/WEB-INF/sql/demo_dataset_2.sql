INSERT INTO Users 
	(firstname,lastname,email,password,company,phone,creationDate,isActive,role,managerId)
VALUES 
	('admin','noLastname','admin@pimpmytrainee.fr','akTJSwyjM7NW1dSIjv+DwO3pS6enu+ASoZ2zyQpiAQP+oXdQ47iulw==','noCompany','noPhone',NOW(),1,'admin',NULL),
	('trainee','noLastname','trainee@pimpmytrainee.fr','akTJSwyjM7NW1dSIjv+DwO3pS6enu+ASoZ2zyQpiAQP+oXdQ47iulw==','noCompany','noPhone',NOW(),1,'trainee',1);