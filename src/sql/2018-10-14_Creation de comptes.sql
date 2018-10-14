CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` text,
  `lastname` text,
  `email` varchar(90) NOT NULL,
  `password` text NOT NULL,
  `company` text,
  `phone` text,
  `createDate` date NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `role` enum('admin','trainee') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci