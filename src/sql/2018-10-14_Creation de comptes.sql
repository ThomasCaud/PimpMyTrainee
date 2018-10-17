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
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci
