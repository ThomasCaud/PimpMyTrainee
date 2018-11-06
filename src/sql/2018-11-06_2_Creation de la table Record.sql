CREATE TABLE `records` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  	`score` int NOT NULL DEFAULT 0,
  	`duration` int NOT NULL DEFAULT 0,
  	`quiz` bigint(20) unsigned NOT NULL,
  	`trainee` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`id`),
	FOREIGN KEY (quiz) REFERENCES Quizzes(id) ON DELETE CASCADE,
	FOREIGN KEY (trainee) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `RecordAnswer` (
	`record` bigint(20) unsigned NOT NULL,
	`answer` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`record`,`answer`),
	FOREIGN KEY (record) REFERENCES Records(id) ON DELETE CASCADE,
	FOREIGN KEY (answer) REFERENCES Answers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;