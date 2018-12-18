CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(60),
  `lastname` varchar(60),
  `email` varchar(90) NOT NULL,
  `password` char(56) NOT NULL,
  `company` varchar(90),
  `phone` varchar(13),
  `creationDate` datetime NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `role` enum('admin','trainee') NOT NULL,
  `managerId` bigint(20) unsigned,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  FOREIGN KEY (managerId) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `themes` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`label` varchar(90) NOT NULL,
  
	PRIMARY KEY (`id`),
	UNIQUE KEY `label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

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

CREATE TABLE `questions` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`label` varchar(255) NOT NULL,
  	`isActive` tinyint(1) NOT NULL,
  	`position` int NOT NULL DEFAULT -1,
  	`quiz` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`id`),
	FOREIGN KEY (quiz) REFERENCES Quizzes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `answers` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  	`label` varchar(255) NOT NULL,
  	`isCorrect` tinyint(1) NOT NULL,
  	`isActive` tinyint(1) NOT NULL,
  	`position` int NOT NULL DEFAULT -1,
  	`question` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`id`),
	FOREIGN KEY (question) REFERENCES Questions(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `records` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  	`score` int NOT NULL DEFAULT 0,
  	`duration` int NOT NULL DEFAULT 0,
  	`quiz` bigint(20) unsigned NOT NULL,
  	`trainee` bigint(20) unsigned NOT NULL,
  	`contextId` varchar(36),
	PRIMARY KEY (`id`),
	FOREIGN KEY (quiz) REFERENCES Quizzes(id) ON DELETE CASCADE,
	FOREIGN KEY (trainee) REFERENCES Users(id) ON DELETE CASCADE,
	CONSTRAINT constr UNIQUE(quiz,trainee)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `recordanswer` (
	`record` bigint(20) unsigned NOT NULL,
	`answer` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`record`,`answer`),
	FOREIGN KEY (record) REFERENCES Records(id) ON DELETE CASCADE,
	FOREIGN KEY (answer) REFERENCES Answers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;