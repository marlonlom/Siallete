-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-07-2014 a las 06:08:48
-- Versión del servidor: 5.6.16
-- Versión de PHP: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `s2c_demo`
--
CREATE DATABASE IF NOT EXISTS `s2c_demo` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `s2c_demo`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conceptos_precio`
--

DROP TABLE IF EXISTS `conceptos_precio`;
CREATE TABLE IF NOT EXISTS `conceptos_precio` (
  `codigo` int(10) NOT NULL AUTO_INCREMENT,
  `concepto` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `clasificacion` int(1) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `creado` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `concepto` (`concepto`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Tabla de conceptos que podrá se asociados a los precios' AUTO_INCREMENT=12 ;

--
-- Volcado de datos para la tabla `conceptos_precio`
--

INSERT INTO `conceptos_precio` (`codigo`, `concepto`, `clasificacion`, `descripcion`, `creado`) VALUES
(1, 'nueva tarea', 21, 'una estructura ahi', '2014-07-09 16:45:06'),
(3, 'tarea 2', 31, 'una cosa ahi', '2014-07-09 21:46:46'),
(4, 'precio 3', 21, 'otra vez estructurado', '2014-07-09 21:49:06'),
(5, 'tarea 3ds', 1, 'sdkjhlasdfjkahlfjkasdlfkasj', '2014-07-10 03:18:36'),
(7, 'precio 34', 21, 'sdfasdgfasdfasdflajshxnalsdhaslk', '2014-07-10 03:27:19'),
(8, 'nueva tarea22', 31, 'nueva tarea jajajaja jajaja', '2014-07-10 04:07:32'),
(11, 'aja loco', 1, 'sldfkjsdglfk', '2014-07-10 03:15:55');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
