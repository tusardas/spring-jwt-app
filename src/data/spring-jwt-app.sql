-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.2.0 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table spring-jwt-app.note
CREATE TABLE IF NOT EXISTS `note` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Dumping data for table spring-jwt-app.note: ~10 rows (approximately)
INSERT INTO `note` (`id`, `content`) VALUES
	(1, 'Todo list for items in 2024'),
	(2, 'Todo list for items in 2025'),
	(3, 'Todo list for items in 2026'),
	(4, 'Todo list for items in 2027'),
	(5, 'Todo list for items in 2028'),
	(6, 'Todo list for items in 2029'),
	(7, 'Todo list for items in 2030'),
	(8, 'Todo list for items in 2031'),
	(9, 'Todo list for items in 2032'),
	(10, 'Todo list for items in 2033');

-- Dumping structure for table spring-jwt-app.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE latin1_bin NOT NULL,
  `role` varchar(255) COLLATE latin1_bin NOT NULL,
  `first_name` varchar(255) COLLATE latin1_bin NOT NULL,
  `last_name` varchar(255) COLLATE latin1_bin NOT NULL,
  `email` varchar(255) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

-- Dumping data for table spring-jwt-app.user: ~0 rows (approximately)
INSERT INTO `user` (`id`, `password`, `role`, `first_name`, `last_name`, `email`) VALUES
	(1, 'test123', 'User', 'Test', 'Test', 'test@test.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
