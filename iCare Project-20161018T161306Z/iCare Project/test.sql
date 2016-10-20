-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 18, 2016 at 06:15 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointments`
--

CREATE TABLE `tbl_appointments` (
  `APPOINTMENT_ID` int(11) NOT NULL,
  `DAY_ID` int(11) DEFAULT NULL,
  `TIME_ID` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `EXPIRED_DATE` date DEFAULT NULL,
  `CUSTOMER_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_appointments`
--

INSERT INTO `tbl_appointments` (`APPOINTMENT_ID`, `DAY_ID`, `TIME_ID`, `START_DATE`, `EXPIRED_DATE`, `CUSTOMER_ID`) VALUES
(1, 1, 1, '2016-10-13', '2016-11-13', 1),
(2, 2, 6, '2016-12-10', '2017-01-10', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customers`
--

CREATE TABLE `tbl_customers` (
  `CUSTOMER_ID` int(11) NOT NULL,
  `CUSTOMER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `PHONE` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `ADDRESS` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `EMAIL` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `LOGIN_ID` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATEDAT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATEDAT` datetime NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_customers`
--

INSERT INTO `tbl_customers` (`CUSTOMER_ID`, `CUSTOMER_NAME`, `DOB`, `GENDER`, `PHONE`, `ADDRESS`, `EMAIL`, `LOGIN_ID`, `PASSWORD`, `CREATEDAT`, `UPDATEDAT`, `STATUS`) VALUES
(1, 'Nguyen Thien Phuc', '1996-10-06', 'Male', '0468452599', 'Espoo', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(2, 'Vu Hoang Long', '1996-08-24', 'Male', '0468338859', 'Espoo', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(3, 'Mai Thanh Truc', '1994-10-12', 'Female', '093845732743', 'Ho Chi Minh', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(4, 'Nguyen Quang Minh', '1999-11-18', 'Male', '01234233955', 'Ho Chi Minh', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(5, 'Nguyen Quang Minh', '1890-04-11', 'Male', '01234555847', 'Ho Chi Minh', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(6, 'Nguyen Thien Phuc', '2016-10-06', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1),
(7, 'Nguyen Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, '2016-10-18 10:32:22', '2016-10-18 10:32:22', 1),
(9, 'Nguyễn Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, '2016-10-18 10:49:57', '0000-00-00 00:00:00', 1),
(10, 'Nguyen Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', 'TEST_USERNAME', 'TEST_PASSWORD', '2016-10-18 12:44:28', '2016-10-18 12:45:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_locations`
--

CREATE TABLE `tbl_locations` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ADDRESS` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `LATITUDE` varchar(10) DEFAULT NULL,
  `LONGITUDE` varchar(10) DEFAULT NULL,
  `DATE_CREATED` datetime DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_locations`
--

INSERT INTO `tbl_locations` (`ID`, `NAME`, `ADDRESS`, `LATITUDE`, `LONGITUDE`, `DATE_CREATED`, `STATUS`) VALUES
(1, 'Apple Centre', '1 Infinite Loop Cupertino, CA', '37.331741', '-122.03033', '0000-00-00 00:00:00', 1),
(2, 'Googleplex', '1600 Amphitheatre Pkwy, Mountain View', '37.421999', '-122.08395', '0000-00-00 00:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_time`
--

CREATE TABLE `tbl_time` (
  `TIME_ID` int(11) NOT NULL,
  `TIME` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_time`
--

INSERT INTO `tbl_time` (`TIME_ID`, `TIME`) VALUES
(1, '07:00'),
(2, '07:25'),
(3, '07:50'),
(6, '08:15'),
(7, '08:40'),
(8, '09:05'),
(9, '09:30'),
(10, '09:55'),
(11, '10:20'),
(12, '10:45'),
(13, '10:10'),
(14, '10:35'),
(15, '11:00'),
(16, '11:25'),
(17, '11:50'),
(18, '12:15'),
(19, '12:40'),
(20, '13:05'),
(21, '13:30'),
(22, '13:55'),
(23, '14:20'),
(24, '14:45'),
(25, '15:10'),
(26, '15:35'),
(27, '15:00'),
(28, '16:25'),
(29, '16:50'),
(30, '17:15'),
(31, '17:40'),
(32, '18:05'),
(33, '18:30'),
(34, '18:55'),
(35, '19:20'),
(36, '19:45'),
(37, '20:10'),
(38, '20:35'),
(39, '20:00'),
(40, '20:25'),
(41, '20:50'),
(42, '21:15'),
(43, '21:40'),
(44, '22:05'),
(45, '22:30'),
(46, '06:35');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_weekdays`
--

CREATE TABLE `tbl_weekdays` (
  `DAY_ID` int(11) NOT NULL,
  `DAY` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_weekdays`
--

INSERT INTO `tbl_weekdays` (`DAY_ID`, `DAY`) VALUES
(1, 'Monday'),
(2, 'Tuesday'),
(3, 'Wednesday'),
(4, 'Thursday'),
(5, 'Friday'),
(6, 'Saturday'),
(7, 'Sunday');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD PRIMARY KEY (`APPOINTMENT_ID`),
  ADD KEY `DAY_ID` (`DAY_ID`),
  ADD KEY `TIME_ID` (`TIME_ID`),
  ADD KEY `CUSTOMER_ID` (`CUSTOMER_ID`);

--
-- Indexes for table `tbl_customers`
--
ALTER TABLE `tbl_customers`
  ADD PRIMARY KEY (`CUSTOMER_ID`),
  ADD UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`);

--
-- Indexes for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ID_UNIQUE` (`ID`);

--
-- Indexes for table `tbl_time`
--
ALTER TABLE `tbl_time`
  ADD PRIMARY KEY (`TIME_ID`);

--
-- Indexes for table `tbl_weekdays`
--
ALTER TABLE `tbl_weekdays`
  ADD PRIMARY KEY (`DAY_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  MODIFY `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_customers`
--
ALTER TABLE `tbl_customers`
  MODIFY `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_time`
--
ALTER TABLE `tbl_time`
  MODIFY `TIME_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT for table `tbl_weekdays`
--
ALTER TABLE `tbl_weekdays`
  MODIFY `DAY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD CONSTRAINT `tbl_appointments_ibfk_1` FOREIGN KEY (`DAY_ID`) REFERENCES `tbl_weekdays` (`DAY_ID`),
  ADD CONSTRAINT `tbl_appointments_ibfk_2` FOREIGN KEY (`TIME_ID`) REFERENCES `tbl_time` (`TIME_ID`),
  ADD CONSTRAINT `tbl_appointments_ibfk_3` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `tbl_customers` (`CUSTOMER_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
