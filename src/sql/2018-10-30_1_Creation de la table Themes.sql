CREATE TABLE `themes` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`label` varchar(90) NOT NULL,
  
	PRIMARY KEY (`id`),
	UNIQUE KEY `label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
