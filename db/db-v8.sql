-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 04-09-2023 a las 21:30:33
-- Versión del servidor: 5.7.24
-- Versión de PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `liga_nunez`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `downloads`
--

CREATE TABLE `downloads` (
  `download_picture_id` varchar(36) NOT NULL,
  `download_user_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `downloads`
--

INSERT INTO `downloads` (`download_picture_id`, `download_user_id`) VALUES
('110c7065-69af-4b13-b25f-f1b2042c7ed4', '5db94485-424d-4ae6-ad82-68b3e3943f71'),
('9e2c9950-8acd-4964-8638-3ee852879ffc', '5db94485-424d-4ae6-ad82-68b3e3943f71'),
('9e2c9950-8acd-4964-8638-3ee852879ffc', '5db94485-424d-4ae6-ad82-68b3e3943f71');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_update_requests`
--

CREATE TABLE `password_update_requests` (
  `request_code` varchar(36) NOT NULL,
  `request_user` varchar(60) NOT NULL,
  `request_creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permission_levels`
--

CREATE TABLE `permission_levels` (
  `permission_level_id` int(11) NOT NULL,
  `permission_level_description` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `permission_levels`
--

INSERT INTO `permission_levels` (`permission_level_id`, `permission_level_description`) VALUES
(2, 'ADMIN'),
(1, 'USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pictures`
--

CREATE TABLE `pictures` (
  `picture_id` varchar(36) NOT NULL,
  `picture_date` date NOT NULL,
  `picture_place` varchar(60) NOT NULL,
  `picture_tournament` varchar(36) NOT NULL,
  `picture_upload_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pictures`
--

INSERT INTO `pictures` (`picture_id`, `picture_date`, `picture_place`, `picture_tournament`, `picture_upload_date`) VALUES
('7997ad70-c0a7-4afb-a006-a1730dbeddca', '2023-07-07', 'Villa Celina', '7c2a977a-1699-11ee-a9da-60189510d1c2', '2023-09-04 18:25:50'),
('cb9fe704-0026-458e-9847-04d9a833b66b', '2023-07-07', 'Villa Celina', '7c2a977a-1699-11ee-a9da-60189510d1c2', '2023-09-04 18:26:21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tournaments`
--

CREATE TABLE `tournaments` (
  `tournament_id` varchar(36) NOT NULL,
  `tournament_name` varchar(30) NOT NULL,
  `tournament_type` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tournaments`
--

INSERT INTO `tournaments` (`tournament_id`, `tournament_name`, `tournament_type`) VALUES
('7c2a977a-1699-11ee-a9da-60189510d1c2', 'torneo de prueba F', 'F'),
('89000444-1699-11ee-a9da-60189510d1c2', 'torneo de prueba M', 'M');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tournament_types`
--

CREATE TABLE `tournament_types` (
  `tournament_type_id` varchar(1) NOT NULL,
  `tournament_type_description` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tournament_types`
--

INSERT INTO `tournament_types` (`tournament_type_id`, `tournament_type_description`) VALUES
('F', 'FEMENINO'),
('M', 'MASCULINO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
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
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `user_email`, `user_password`, `user_name`, `user_age`, `user_address`, `user_permissions`, `user_creation_date`, `user_last_update`) VALUES
('5db94485-424d-4ae6-ad82-68b3e3943f71', 'tamboriniagustin00@gmail.com', '$2a$10$FszQdzzqgfafFKWdtY9omOQ8U2nf0j0x/CVKgZvdw8WnE5vwYecIS', 'Juan Perez', 32, 'Corrientes 800', 2, '2023-06-23 16:30:18', '2023-06-23 16:30:18');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `downloads`
--
ALTER TABLE `downloads`
  ADD KEY `download_picture_id` (`download_picture_id`),
  ADD KEY `download_user_id` (`download_user_id`);

--
-- Indices de la tabla `password_update_requests`
--
ALTER TABLE `password_update_requests`
  ADD PRIMARY KEY (`request_code`),
  ADD UNIQUE KEY `request_user` (`request_user`),
  ADD KEY `request_user_2` (`request_user`);

--
-- Indices de la tabla `permission_levels`
--
ALTER TABLE `permission_levels`
  ADD PRIMARY KEY (`permission_level_id`),
  ADD UNIQUE KEY `permission_level_description` (`permission_level_description`);

--
-- Indices de la tabla `pictures`
--
ALTER TABLE `pictures`
  ADD PRIMARY KEY (`picture_id`),
  ADD KEY `picture_tournament` (`picture_tournament`);

--
-- Indices de la tabla `tournaments`
--
ALTER TABLE `tournaments`
  ADD PRIMARY KEY (`tournament_id`),
  ADD KEY `tournament_type` (`tournament_type`);

--
-- Indices de la tabla `tournament_types`
--
ALTER TABLE `tournament_types`
  ADD PRIMARY KEY (`tournament_type_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_email` (`user_email`),
  ADD KEY `user_permissions` (`user_permissions`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `permission_levels`
--
ALTER TABLE `permission_levels`
  MODIFY `permission_level_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `downloads`
--
ALTER TABLE `downloads`
  ADD CONSTRAINT `downloads_ibfk_1` FOREIGN KEY (`download_picture_id`) REFERENCES `pictures` (`picture_id`),
  ADD CONSTRAINT `downloads_ibfk_2` FOREIGN KEY (`download_user_id`) REFERENCES `users` (`user_id`);

--
-- Filtros para la tabla `password_update_requests`
--
ALTER TABLE `password_update_requests`
  ADD CONSTRAINT `password_update_requests_ibfk_1` FOREIGN KEY (`request_user`) REFERENCES `users` (`user_email`);

--
-- Filtros para la tabla `pictures`
--
ALTER TABLE `pictures`
  ADD CONSTRAINT `pictures_ibfk_1` FOREIGN KEY (`picture_tournament`) REFERENCES `tournaments` (`tournament_id`);

--
-- Filtros para la tabla `tournaments`
--
ALTER TABLE `tournaments`
  ADD CONSTRAINT `tournaments_ibfk_1` FOREIGN KEY (`tournament_type`) REFERENCES `tournament_types` (`tournament_type_id`);

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_permissions`) REFERENCES `permission_levels` (`permission_level_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
