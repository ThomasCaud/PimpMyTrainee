CREATE TABLE `quizzes` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`title` varchar(90) NOT NULL,
  	`theme` bigint(20) unsigned NOT NULL,
  	`creator` bigint(20) unsigned NOT NULL,
  	`creationDate` datetime NOT NULL,
  	`isActive` tinyint(1) NOT NULL,
  	
	PRIMARY KEY (`id`),
	FOREIGN KEY (theme) REFERENCES Themes(id),
	FOREIGN KEY (creator) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
