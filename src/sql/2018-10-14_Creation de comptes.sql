CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` text,
  `lastname` text,
  `email` varchar(90) NOT NULL,
  `password` text NOT NULL,
  `company` text,
  `phone` text,
  `creationDate` datetime NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `role` enum('admin','trainee') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci
