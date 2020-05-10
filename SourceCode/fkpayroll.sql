-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 10, 2020 at 02:06 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fkpayroll`
--

-- --------------------------------------------------------

--
-- Table structure for table `dailywageemployee`
--

CREATE TABLE `dailywageemployee` (
  `emp_id` char(6) NOT NULL,
  `union_member` tinyint(1) NOT NULL DEFAULT 0,
  `union_week_rate` float NOT NULL DEFAULT 0,
  `hour_rate` float NOT NULL,
  `payment_method` char(1) NOT NULL,
  `last_tr_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dailywageemployee`
--

INSERT INTO `dailywageemployee` (`emp_id`, `union_member`, `union_week_rate`, `hour_rate`, `payment_method`, `last_tr_date`) VALUES
('140768', 1, 201, 500.5, 'P', '2020-05-08');

-- --------------------------------------------------------

--
-- Table structure for table `dailywagetrans`
--

CREATE TABLE `dailywagetrans` (
  `emp_id` char(6) NOT NULL,
  `d_date` date NOT NULL,
  `weekday` int(11) NOT NULL,
  `amount` float NOT NULL,
  `type` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dailywagetrans`
--

INSERT INTO `dailywagetrans` (`emp_id`, `d_date`, `weekday`, `amount`, `type`) VALUES
('140456', '2020-05-10', 1, 54, 'D'),
('140512', '2020-05-06', 4, 87, 'D'),
('140512', '2020-05-08', 6, 87, 'D'),
('140512', '2020-05-08', 6, -100, 'S'),
('140512', '2020-05-09', 7, 140, 'D'),
('140512', '2020-05-10', 1, -300, 'S'),
('140768', '2020-05-07', 5, -300, 'S'),
('140768', '2020-05-08', 6, 3503.5, 'D'),
('140768', '2020-05-09', 7, 2502.5, 'D'),
('140768', '2020-05-10', 1, 3003, 'D'),
('140967', '2020-04-18', 7, -250, 'S'),
('140967', '2020-05-10', 1, -200, 'S');

-- --------------------------------------------------------

--
-- Table structure for table `salaryemployee`
--

CREATE TABLE `salaryemployee` (
  `emp_id` char(6) NOT NULL,
  `union_member` tinyint(1) NOT NULL DEFAULT 0,
  `union_week_rate` float NOT NULL DEFAULT 0,
  `payment_method` char(1) NOT NULL,
  `salary` float NOT NULL,
  `last_mtr_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `salaryemployee`
--

INSERT INTO `salaryemployee` (`emp_id`, `union_member`, `union_week_rate`, `payment_method`, `salary`, `last_mtr_date`) VALUES
('140966', 0, 0, 'B', 31000, NULL),
('140967', 1, 234, 'P', 50000, '2020-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `salesemployee`
--

CREATE TABLE `salesemployee` (
  `emp_id` char(6) NOT NULL,
  `union_member` tinyint(1) NOT NULL DEFAULT 0,
  `union_week_rate` float NOT NULL DEFAULT 0,
  `payment_method` char(1) NOT NULL,
  `salary` float NOT NULL,
  `comm_rate` float NOT NULL,
  `last_mtr_date` date DEFAULT NULL,
  `last_ftr_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `salesemployee`
--

INSERT INTO `salesemployee` (`emp_id`, `union_member`, `union_week_rate`, `payment_method`, `salary`, `comm_rate`, `last_mtr_date`, `last_ftr_date`) VALUES
('140456', 0, 0, 'A', 40000, 2.7, NULL, NULL),
('140512', 1, 200.1, 'B', 31000, 2.9, '2020-04-30', '2020-05-08');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dailywageemployee`
--
ALTER TABLE `dailywageemployee`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `dailywagetrans`
--
ALTER TABLE `dailywagetrans`
  ADD PRIMARY KEY (`emp_id`,`d_date`,`type`);

--
-- Indexes for table `salaryemployee`
--
ALTER TABLE `salaryemployee`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `salesemployee`
--
ALTER TABLE `salesemployee`
  ADD PRIMARY KEY (`emp_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
