-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 22, 2023 at 06:21 PM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `liga_nunez`
--

-- --------------------------------------------------------

--
-- Table structure for table `password_update_requests`
--

CREATE TABLE `password_update_requests` (
  `request_code` int(11) NOT NULL,
  `request_user` varchar(36) NOT NULL,
  `request_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `permission_levels`
--

CREATE TABLE `permission_levels` (
  `permission_level_id` int(11) NOT NULL,
  `permission_level_description` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permission_levels`
--

INSERT INTO `permission_levels` (`permission_level_id`, `permission_level_description`) VALUES
(2, 'ADMIN'),
(1, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `pictures`
--

CREATE TABLE `pictures` (
  `picture_id` varchar(36) NOT NULL,
  `picture_date` datetime NOT NULL,
  `picture_place` varchar(60) NOT NULL,
  `picture_upload_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tournaments`
--

CREATE TABLE `tournaments` (
  `tournament_id` varchar(36) NOT NULL,
  `tournament_code` int(11) NOT NULL,
  `tournament_name` varchar(30) NOT NULL,
  `tournament_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tournament_types`
--

CREATE TABLE `tournament_types` (
  `tournament_type_id` int(11) NOT NULL,
  `tournament_type_description` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(36) NOT NULL,
  `user_email` varchar(60) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_age` int(11) NOT NULL,
  `user_address` varchar(60) NOT NULL,
  `user_permissions` int(11) NOT NULL DEFAULT '1',
  `user_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_last_update` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_email`, `user_password`, `user_name`, `user_age`, `user_address`, `user_permissions`, `user_creation_date`, `user_last_update`) VALUES
('523bae96-8b28-4afb-8d15-541934d4a7f7', 'example@gmail.com', '$2a$10$Em.hRaWIx8IyKPmDvJ6mZOn9SP2CnkwE7iaqVegtXYQtFkT0Nj7ZC', 'Juan Perez', 32, 'Corrientes 800', 1, '2023-06-22 15:13:47', '2023-06-22 15:13:47');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `password_update_requests`
--
ALTER TABLE `password_update_requests`
  ADD PRIMARY KEY (`request_code`),
  ADD UNIQUE KEY `request_user` (`request_user`),
  ADD KEY `request_user_2` (`request_user`);

--
-- Indexes for table `permission_levels`
--
ALTER TABLE `permission_levels`
  ADD PRIMARY KEY (`permission_level_id`),
  ADD UNIQUE KEY `permission_level_description` (`permission_level_description`);

--
-- Indexes for table `pictures`
--
ALTER TABLE `pictures`
  ADD PRIMARY KEY (`picture_id`);

--
-- Indexes for table `tournaments`
--
ALTER TABLE `tournaments`
  ADD PRIMARY KEY (`tournament_id`),
  ADD UNIQUE KEY `tournament_code` (`tournament_code`),
  ADD KEY `tournament_type` (`tournament_type`);

--
-- Indexes for table `tournament_types`
--
ALTER TABLE `tournament_types`
  ADD PRIMARY KEY (`tournament_type_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_email` (`user_email`),
  ADD KEY `user_permissions` (`user_permissions`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `permission_levels`
--
ALTER TABLE `permission_levels`
  MODIFY `permission_level_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tournament_types`
--
ALTER TABLE `tournament_types`
  MODIFY `tournament_type_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `password_update_requests`
--
ALTER TABLE `password_update_requests`
  ADD CONSTRAINT `password_update_requests_ibfk_1` FOREIGN KEY (`request_user`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `tournaments`
--
ALTER TABLE `tournaments`
  ADD CONSTRAINT `tournaments_ibfk_1` FOREIGN KEY (`tournament_type`) REFERENCES `tournament_types` (`tournament_type_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_permissions`) REFERENCES `permission_levels` (`permission_level_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
