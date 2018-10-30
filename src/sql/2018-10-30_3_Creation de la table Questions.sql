CREATE TABLE `questions` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`label` varchar(255) NOT NULL,
  	`isActive` tinyint(1) NOT NULL,
  	`position` int NOT NULL DEFAULT -1,
  	`quiz` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`id`),
	FOREIGN KEY (quiz) REFERENCES Quizzes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
