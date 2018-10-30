CREATE TABLE `possibleanswers` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  	`label` varchar(255) NOT NULL,
  	`isCorrect` tinyint(1) NOT NULL,
  	`isActive` tinyint(1) NOT NULL,
  	`position` int NOT NULL DEFAULT -1,
  	`question` bigint(20) unsigned NOT NULL,

	PRIMARY KEY (`id`),
	FOREIGN KEY (question) REFERENCES Questions(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
