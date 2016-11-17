-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 16, 2016 at 10:53 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `icaredb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointments`
--

CREATE TABLE `tbl_appointments` (
  `APPOINTMENT_ID` int(11) NOT NULL,
  `START_DATE` date DEFAULT NULL,
  `EXPIRED_DATE` date DEFAULT NULL,
  `TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL DEFAULT 'Cố định',
  `CUSTOMER_ID` int(11) DEFAULT NULL,
  `LOCATION_ID` int(11) NOT NULL,
  `VOUCHER_ID` int(11) NOT NULL,
  `VERIFICATION_CODE` binary(7) NOT NULL,
  `ISCONFIRMED` tinyint(1) NOT NULL DEFAULT '0',
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_appointments`
--

INSERT INTO `tbl_appointments` (`APPOINTMENT_ID`, `START_DATE`, `EXPIRED_DATE`, `TYPE`, `CUSTOMER_ID`, `LOCATION_ID`, `VOUCHER_ID`, `VERIFICATION_CODE`, `ISCONFIRMED`, `ACTIVE`) VALUES
(1, '2016-10-13', '2016-11-13', 'Cố định', 1, 1, 1, 0x00000000000000, 0, 1),
(2, '2016-12-10', '2017-01-10', 'Cố định', 1, 1, 2, 0x00000000000000, 0, 1),
(3, '2016-11-10', '2016-12-10', 'Cố định', 5, 1, 2, 0x00000000000000, 0, 0),
(4, '2016-11-30', '2016-12-30', 'Cố định', 9, 1, 1, 0x00000000000000, 0, 0),
(5, '2016-11-16', '2016-12-16', 'Cố định', 1, 1, 1, 0x00000000000000, 0, 0),
(6, '2016-11-17', '2016-12-17', 'Cố định', 4, 1, 2, 0x00000000000000, 0, 0),
(7, '2016-11-09', '2016-12-09', 'Cố định', 3, 1, 1, 0x00000000000000, 0, 0),
(8, '2016-11-02', '2016-12-02', 'Cố định', 3, 1, 2, 0x00000000000000, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointmentschedule`
--

CREATE TABLE `tbl_appointmentschedule` (
  `ID` int(11) NOT NULL,
  `TIME_ID` int(11) NOT NULL,
  `DAY_ID` int(11) NOT NULL,
  `APPOINTMENT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_appointmentschedule`
--

INSERT INTO `tbl_appointmentschedule` (`ID`, `TIME_ID`, `DAY_ID`, `APPOINTMENT_ID`) VALUES
(1, 8, 2, 1),
(2, 12, 3, 1),
(3, 8, 4, 1),
(6, 7, 1, 2),
(7, 16, 4, 2),
(14, 15, 6, 3),
(15, 6, 4, 4),
(16, 24, 3, 4),
(17, 6, 4, 4),
(18, 7, 3, 5),
(19, 6, 1, 6),
(20, 8, 5, 6),
(21, 3, 5, 7),
(22, 12, 2, 8),
(23, 25, 5, 8);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_cities`
--

CREATE TABLE `tbl_cities` (
  `CITY_ID` int(11) NOT NULL,
  `CITY` varchar(70) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `COUNTRY_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_cities`
--

INSERT INTO `tbl_cities` (`CITY_ID`, `CITY`, `COUNTRY_ID`) VALUES
(1, 'An Giang', 235),
(2, 'Bà Rịa - Vũng Tàu', 235),
(3, 'Bắc Giang', 235),
(4, 'Bắc Kạn', 235),
(5, 'Bạc Liêu', 235),
(6, 'Bắc Ninh', 235),
(7, 'Bến Tre', 235),
(8, 'Bình Định', 235),
(9, 'Bình Dương', 235),
(10, 'Bình Phước', 235),
(11, 'Bình Thuận', 235),
(12, 'Cà Mau', 235),
(13, 'Cần Thơ', 235),
(14, 'Cao Bằng', 235),
(15, 'Đà Nẵng', 235),
(16, 'Đắk Lắk', 235),
(17, 'Đắk Nông', 235),
(18, 'Điện Biên', 235),
(19, 'Đồng Nai', 235),
(20, 'Đồng Tháp', 235),
(21, 'Gia Lai', 235),
(22, 'Hà Giang', 235),
(23, 'Hà Nam', 235),
(24, 'Hà Nội', 235),
(25, 'Hà Tĩnh', 235),
(26, 'Hải Dương', 235),
(27, 'Hải Phòng', 235),
(28, 'Hậu Giang', 235),
(29, 'Hòa Bình', 235),
(30, 'Hưng Yên', 235),
(31, 'Khánh Hòa', 235),
(32, 'Kiên Giang', 235),
(33, 'Kon Tum', 235),
(34, 'Lai Châu', 235),
(35, 'Lâm Đồng', 235),
(36, 'Lạng Sơn', 235),
(37, 'Lào Cai', 235),
(38, 'Long An', 235),
(39, 'Nam Định', 235),
(40, 'Nghệ An', 235),
(41, 'Ninh Bình', 235),
(42, 'Ninh Thuận', 235),
(43, 'Phú Thọ', 235),
(44, 'Phú Yên', 235),
(45, 'Quảng Bình', 235),
(46, 'Quảng Nam', 235),
(47, 'Quảng Ngãi', 235),
(48, 'Quảng Ninh', 235),
(49, 'Quảng Trị', 235),
(50, 'Sóc Trăng', 235),
(51, 'Sơn La', 235),
(52, 'Tây Ninh', 235),
(53, 'Thái Bình', 235),
(54, 'Thái Nguyên', 235),
(55, 'Thanh Hóa', 235),
(56, 'Thừa Thiên Huế', 235),
(57, 'Tiền Giang', 235),
(58, 'Hồ Chí Minh', 235),
(59, 'Trà Vinh', 235),
(60, 'Tuyên Quang', 235),
(61, 'Vĩnh Long', 235),
(62, 'Vĩnh Phúc', 235),
(63, 'Yên Bái', 235);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_countries`
--

CREATE TABLE `tbl_countries` (
  `COUNTRY_ID` int(11) NOT NULL,
  `COUNTRY` varchar(70) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_countries`
--

INSERT INTO `tbl_countries` (`COUNTRY_ID`, `COUNTRY`) VALUES
(1, 'Afghanistan'),
(2, 'Albania'),
(3, 'Algeria'),
(4, 'American Samoa'),
(5, 'Andorra'),
(6, 'Angola'),
(7, 'Anguilla'),
(8, 'Antarctica'),
(9, 'Antigua and Barbuda'),
(10, 'Argentina'),
(11, 'Armenia'),
(12, 'Aruba'),
(13, 'Australia'),
(14, 'Austria'),
(15, 'Azerbaijan'),
(16, 'Bahrain'),
(17, 'Bangladesh'),
(18, 'Barbados'),
(19, 'Belarus'),
(20, 'Belgium'),
(21, 'Belize'),
(22, 'Benin'),
(23, 'Bermuda'),
(24, 'Bhutan'),
(25, 'Bolivia'),
(26, 'Bosnia and Herzegovina'),
(27, 'Botswana'),
(28, 'Bouvet Island'),
(29, 'Brazil'),
(30, 'British Indian Ocean Territory'),
(31, 'British Virgin Islands'),
(32, 'Brunei'),
(33, 'Bulgaria'),
(34, 'Burkina Faso'),
(35, 'Burundi'),
(36, 'Cambodia'),
(37, 'Cameroon'),
(38, 'Canada'),
(39, 'Cape Verde'),
(40, 'Cayman Islands'),
(41, 'Central African Republic'),
(42, 'Chad'),
(43, 'Chile'),
(44, 'China'),
(45, 'Christmas Island'),
(46, 'Cocos (Keeling) Islands'),
(47, 'Colombia'),
(48, 'Comoros'),
(49, 'Congo'),
(50, 'Cook Islands'),
(51, 'Costa Rica'),
(52, 'Cote d''Ivoire'),
(53, 'Croatia'),
(54, 'Cuba'),
(55, 'Cyprus'),
(56, 'Czech Republic'),
(57, 'Democratic Republic of the Congo'),
(58, 'Denmark'),
(59, 'Djibouti'),
(60, 'Dominica'),
(61, 'Dominican Republic'),
(62, 'East Timor'),
(63, 'Ecuador'),
(64, 'Egypt'),
(65, 'El Salvador'),
(66, 'Equatorial Guinea'),
(67, 'Eritrea'),
(68, 'Estonia'),
(69, 'Ethiopia'),
(70, 'Faeroe Islands'),
(71, 'Falkland Islands'),
(72, 'Fiji'),
(73, 'Finland'),
(74, 'Former Yugoslav Republic of Macedonia'),
(75, 'France'),
(76, 'French Guiana'),
(77, 'French Polynesia'),
(78, 'French Southern Territories'),
(79, 'Gabon'),
(80, 'Georgia'),
(81, 'Germany'),
(82, 'Ghana'),
(83, 'Gibraltar'),
(84, 'Greece'),
(85, 'Greenland'),
(86, 'Grenada'),
(87, 'Guadeloupe'),
(88, 'Guam'),
(89, 'Guatemala'),
(90, 'Guinea'),
(91, 'Guinea-Bissau'),
(92, 'Guyana'),
(93, 'Haiti'),
(94, 'Heard Island and McDonald Islands'),
(95, 'Honduras'),
(96, 'Hong Kong'),
(97, 'Hungary'),
(98, 'Iceland'),
(99, 'India'),
(100, 'Indonesia'),
(101, 'Iran'),
(102, 'Iraq'),
(103, 'Ireland'),
(104, 'Israel'),
(105, 'Italy'),
(106, 'Jamaica'),
(107, 'Japan'),
(108, 'Jordan'),
(109, 'Kazakhstan'),
(110, 'Kenya'),
(111, 'Kiribati'),
(112, 'Kuwait'),
(113, 'Kyrgyzstan'),
(114, 'Laos'),
(115, 'Latvia'),
(116, 'Lebanon'),
(117, 'Lesotho'),
(118, 'Liberia'),
(119, 'Libya'),
(120, 'Liechtenstein'),
(121, 'Lithuania'),
(122, 'Luxembourg'),
(123, 'Macau'),
(124, 'Madagascar'),
(125, 'Malawi'),
(126, 'Malaysia'),
(127, 'Maldives'),
(128, 'Mali'),
(129, 'Malta'),
(130, 'Marshall Islands'),
(131, 'Martinique'),
(132, 'Mauritania'),
(133, 'Mauritius'),
(134, 'Mayotte'),
(135, 'Mexico'),
(136, 'Micronesia'),
(137, 'Moldova'),
(138, 'Monaco'),
(139, 'Mongolia'),
(140, 'Montenegro'),
(141, 'Montserrat'),
(142, 'Morocco'),
(143, 'Mozambique'),
(144, 'Myanmar'),
(145, 'Namibia'),
(146, 'Nauru'),
(147, 'Nepal'),
(148, 'Netherlands'),
(149, 'Netherlands Antilles'),
(150, 'New Caledonia'),
(151, 'New Zealand'),
(152, 'Nicaragua'),
(153, 'Niger'),
(154, 'Nigeria'),
(155, 'Niue'),
(156, 'Norfolk Island'),
(157, 'North Korea'),
(158, 'Northern Marianas'),
(159, 'Norway'),
(160, 'Oman'),
(161, 'Pakistan'),
(162, 'Palau'),
(163, 'Panama'),
(164, 'Papua New Guinea'),
(165, 'Paraguay'),
(166, 'Peru'),
(167, 'Philippines'),
(168, 'Pitcairn Islands'),
(169, 'Poland'),
(170, 'Portugal'),
(171, 'Puerto Rico'),
(172, 'Qatar'),
(173, 'Reunion'),
(174, 'Romania'),
(175, 'Russia'),
(176, 'Rwanda'),
(177, 'Sqo Tome and Principe'),
(178, 'Saint Helena'),
(179, 'Saint Kitts and Nevis'),
(180, 'Saint Lucia'),
(181, 'Saint Pierre and Miquelon'),
(182, 'Saint Vincent and the Grenadines'),
(183, 'Samoa'),
(184, 'San Marino'),
(185, 'Saudi Arabia'),
(186, 'Senegal'),
(187, 'Serbia'),
(188, 'Seychelles'),
(189, 'Sierra Leone'),
(190, 'Singapore'),
(191, 'Slovakia'),
(192, 'Slovenia'),
(193, 'Solomon Islands'),
(194, 'Somalia'),
(195, 'South Africa'),
(196, 'South Georgia and the South Sandwich Islands'),
(197, 'South Korea'),
(198, 'South Sudan'),
(199, 'Spain'),
(200, 'Sri Lanka'),
(201, 'Sudan'),
(202, 'Suriname'),
(203, 'Svalbard and Jan Mayen'),
(204, 'Swaziland'),
(205, 'Sweden'),
(206, 'Switzerland'),
(207, 'Syria'),
(208, 'Taiwan'),
(209, 'Tajikistan'),
(210, 'Tanzania'),
(211, 'Thailand'),
(212, 'The Bahamas'),
(213, 'The Gambia'),
(214, 'Togo'),
(215, 'Tokelau'),
(216, 'Tonga'),
(217, 'Trinidad and Tobago'),
(218, 'Tunisia'),
(219, 'Turkey'),
(220, 'Turkmenistan'),
(221, 'Turks and Caicos Islands'),
(222, 'Tuvalu'),
(223, 'Virgin Islands'),
(224, 'Uganda'),
(225, 'Ukraine'),
(226, 'United Arab Emirates'),
(227, 'United Kingdom'),
(228, 'United States'),
(229, 'United States Minor Outlying Islands'),
(230, 'Uruguay'),
(231, 'Uzbekistan'),
(232, 'Vanuatu'),
(233, 'Vatican City'),
(234, 'Venezuela'),
(235, 'Vietnam'),
(236, 'Wallis and Futuna'),
(237, 'Western Sahara'),
(238, 'Yemen'),
(239, 'Yugoslavia'),
(240, 'Zambia'),
(241, 'Zimbabwe');

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
  `UISAVEDSTEP` int(11) DEFAULT NULL,
  `LOGIN_ID` binary(64) DEFAULT NULL,
  `PASSWORD` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATEDAT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATEDAT` datetime NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `SALT` char(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_customers`
--

INSERT INTO `tbl_customers` (`CUSTOMER_ID`, `CUSTOMER_NAME`, `DOB`, `GENDER`, `PHONE`, `ADDRESS`, `EMAIL`, `UISAVEDSTEP`, `LOGIN_ID`, `PASSWORD`, `CREATEDAT`, `UPDATEDAT`, `STATUS`, `ACTIVE`, `SALT`) VALUES
(1, 'Nguyen Thien Phuc', '1996-10-06', 'Male', '0468452599', 'Espoo', NULL, NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(2, 'Vu Hoang Long', '1996-08-24', 'Male', '0468338859', 'Espoo', NULL, NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(3, 'Mai Thanh Truc', '1994-10-12', 'Female', '093845732743', 'Ho Chi Minh', NULL, NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(4, 'Nguyen Quang Minh', '1999-11-18', 'Male', '01234233955', 'Ho Chi Minh', NULL, NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(5, 'Nguyen Quang Minh', '1890-04-11', 'Male', '01234555847', 'Ho Chi Minh', NULL, NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(6, 'Nguyen Thien Phuc', '2016-10-06', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, NULL, '2016-10-18 10:26:15', '2016-10-18 10:27:09', 1, 0, ''),
(7, 'Nguyen Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, NULL, '2016-10-18 10:32:22', '2016-10-18 10:32:22', 1, 0, ''),
(9, 'Nguyễn Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', NULL, NULL, NULL, '2016-10-18 10:49:57', '0000-00-00 00:00:00', 1, 0, ''),
(10, 'Nguyen Quang Minh', '1999-11-18', 'Male', '0468452599', 'HO CHI MINH', '', NULL, 0x544553545f555345524e414d45000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 'TEST_PASSWORD', '2016-10-18 12:44:28', '2016-10-18 12:45:00', 1, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_districts`
--

CREATE TABLE `tbl_districts` (
  `DISTRICT_ID` int(11) NOT NULL,
  `DISTRICT` varchar(50) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `CITY_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_districts`
--

INSERT INTO `tbl_districts` (`DISTRICT_ID`, `DISTRICT`, `CITY_ID`) VALUES
(1, 'An Phú', 1),
(2, 'Châu Đốc', 1),
(3, 'Châu Phú', 1),
(4, 'Châu Thành', 1),
(5, 'Chợ Mới', 1),
(6, 'Long Xuyên', 1),
(7, 'Phú Tân', 1),
(8, 'Tân Châu', 1),
(9, 'Thoại Sơn', 1),
(10, 'Tịnh Biên', 1),
(11, 'Tri Tôn', 1),
(12, 'Bà Rịa', 2),
(13, 'Châu Đức', 2),
(14, 'Côn Đảo', 2),
(15, 'Đất Đỏ', 2),
(16, 'Long Điền', 2),
(17, 'Tân Thành', 2),
(18, 'Vũng Tàu', 2),
(19, 'Xuyên Mộc', 2),
(20, 'Bắc Giang', 3),
(21, 'Hiệp Hòa', 3),
(22, 'Lạng Giang', 3),
(23, 'Lục Nam', 3),
(24, 'Lục Ngạn', 3),
(25, 'Sơn Động', 3),
(26, 'Tân Yên', 3),
(27, 'Việt Yên', 3),
(28, 'Yên Dũng', 3),
(29, 'Yên Thế', 3),
(30, 'Ba Bể', 4),
(31, 'Bắc Kạn', 4),
(32, 'Bạch Thông', 4),
(33, 'Chợ Đồn', 4),
(34, 'Chợ Mới', 4),
(35, 'Na Rì', 4),
(36, 'Ngân Sơn', 4),
(37, 'Pác Nặm', 4),
(38, 'Bạc Liêu', 5),
(39, 'Đông Hải', 5),
(40, 'Giá Rai', 5),
(41, 'Hòa Bình', 5),
(42, 'Hồng Dân', 5),
(43, 'Phước Long', 5),
(44, 'Vĩnh Lợi', 5),
(45, 'Bắc Ninh', 6),
(46, 'Gia Bình', 6),
(47, 'Lương Tài', 6),
(48, 'Quế Võ', 6),
(49, 'Thuận Thành', 6),
(50, 'Tiên Du', 6),
(51, 'Từ Sơn', 6),
(52, 'Yên Phong', 6),
(53, 'Ba Tri', 7),
(54, 'Bến Tre', 7),
(55, 'Bình Đại', 7),
(56, 'Châu Thành', 7),
(57, 'Chợ Lách', 7),
(58, 'Giồng Trôm', 7),
(59, 'Mỏ Cày', 7),
(60, 'Thạnh Phú', 7),
(61, 'An Lão', 8),
(62, 'An Nhơn', 8),
(63, 'Hoài Ân', 8),
(64, 'Hoài Nhơn', 8),
(65, 'Phù Cát', 8),
(66, 'Phù Mỹ', 8),
(67, 'Qui Nhơn', 8),
(68, 'Tây Sơn', 8),
(69, 'Tuy Phước', 8),
(70, 'Vân Canh', 8),
(71, 'Vĩnh Thạnh', 8),
(72, 'Bến Cát', 9),
(73, 'Dầu Tiếng', 9),
(74, 'Dĩ An', 9),
(75, 'Phú Giáo', 9),
(76, 'Tân Uyên', 9),
(77, 'Thủ Dầu Một', 9),
(78, 'Thuận An', 9),
(79, 'Bình Long', 10),
(80, 'Bù Đăng', 10),
(81, 'Bù Đốp', 10),
(82, 'Chơn Thành', 10),
(83, 'Đồng Phú', 10),
(84, 'Đồng Xoài', 10),
(85, 'Lộc Ninh', 10),
(86, 'Phước Long', 10),
(87, 'Hớn Quản', 10),
(88, 'Bù Gia Mập', 10),
(89, 'Bắc Bình', 11),
(90, 'Đức Linh', 11),
(91, 'Hàm Tân', 11),
(92, 'Hàm Thuận Bắc', 11),
(93, 'Hàm Thuận Nam', 11),
(94, 'La Gi', 11),
(95, 'Phan Thiết', 11),
(96, 'Phú Quý', 11),
(97, 'Tánh Linh', 11),
(98, 'Tuy Phong', 11),
(99, 'Cà Mau', 12),
(100, 'Đầm Dơi', 12),
(101, 'Cái Nước', 12),
(102, 'Năm Căn', 12),
(103, 'Ngọc Hiển', 12),
(104, 'Phú Tân', 12),
(105, 'Thới Bình', 12),
(106, 'Trần Văn Thời', 12),
(107, 'U Minh', 12),
(108, 'Bình Thủy', 13),
(109, 'Cái Răng', 13),
(110, 'Cờ Đỏ', 13),
(111, 'Cần Thơ', 13),
(112, 'Ninh Kiều', 13),
(113, 'Ô Môn', 13),
(114, 'Phong Điền', 13),
(115, 'Thốt Nốt', 13),
(116, 'Vĩnh Thạnh', 13),
(117, 'Thới Lai', 13),
(118, 'Bảo Lạc', 14),
(119, 'Bảo Lâm', 14),
(120, 'Cao Bằng', 14),
(121, 'Hạ Lang', 14),
(122, 'Hà Quảng', 14),
(123, 'Hòa An', 14),
(124, 'Nguyên Bình', 14),
(125, 'Phục Hòa', 14),
(126, 'Quảng Uyên', 14),
(127, 'Thạch An', 14),
(128, 'Thông Nông', 14),
(129, 'Trà Lĩnh', 14),
(130, 'Trùng Khánh', 14),
(131, 'Cẩm Lệ', 15),
(132, 'Hải Châu', 15),
(133, 'Hòa Vang', 15),
(134, 'Hoàng Sa', 15),
(135, 'Liên Chiểu', 15),
(136, 'Ngũ Hành Sơn', 15),
(137, 'Sơn Trà', 15),
(138, 'Thanh Khê', 15),
(139, 'Buôn Đôn', 16),
(140, 'Buôn Ma Thuột', 16),
(143, 'Cư Mgar', 16),
(144, 'Cư Kuin', 16),
(145, 'Ea Hleo', 16),
(146, 'Ea Kar', 16),
(147, 'Ea Súp', 16),
(148, 'Krông Ana', 16),
(149, 'Krông Bông', 16),
(150, 'Krông Buk', 16),
(151, 'Krông Năng', 16),
(152, 'Krông Pắk', 16),
(153, 'Lắk', 16),
(154, 'M Đrăk', 16),
(155, 'Buôn Hồ', 16),
(156, 'Cư Jút', 17),
(157, 'Đắk Glong', 17),
(158, 'Đắk Mil', 17),
(159, 'Đắk R Lấp', 17),
(160, 'Đắk Song', 17),
(161, 'Gia Nghĩa', 17),
(162, 'Krông Nô', 17),
(163, 'Tuy Đức', 17),
(164, 'Điện Biên', 18),
(165, 'Điện Biên Đông', 18),
(166, 'Điện Biên Phủ', 18),
(167, 'Mường Chà', 18),
(168, 'Mường Nhé', 18),
(169, 'Tủa Chùa', 18),
(170, 'Tuần Giáo', 18),
(171, 'Biên Hòa', 19),
(172, 'Cẩm Mỹ', 19),
(173, 'Định Quán', 19),
(174, 'Long Khánh', 19),
(175, 'Long Thành', 19),
(176, 'Nhơn Trạch', 19),
(177, 'Tân Phú', 19),
(178, 'Thống Nhất', 19),
(179, 'Trảng Bom', 19),
(180, 'Vĩnh Cữu', 19),
(181, 'Xuân Lộc', 19),
(182, 'Cao Lãnh', 20),
(183, 'Châu Thành', 20),
(184, 'Hồng Ngự', 20),
(185, 'Lai Vung', 20),
(186, 'Lấp Vò', 20),
(187, 'Sa Đéc', 20),
(188, 'Tam Nông', 20),
(189, 'Tân Hồng', 20),
(190, 'Thanh Bình', 20),
(191, 'Tháp Mười', 20),
(192, 'Ayun Pa', 21),
(193, 'An Khê', 21),
(194, 'Chư Păh', 21),
(195, 'Chư Prông', 21),
(196, 'Chư Sê', 21),
(197, 'Đắk Đoa', 21),
(198, 'Đắk Pơ', 21),
(199, 'Đức Cơ', 21),
(200, 'Ia Grai', 21),
(201, 'Ia Pa', 21),
(202, 'K Bang', 21),
(203, 'Kông Chro', 21),
(204, 'Krông Pa', 21),
(205, 'Mang Yang', 21),
(206, 'Phú Thiện', 21),
(207, 'Pleiku', 21),
(208, 'Chư Pưh', 21),
(209, 'Bắc Mê', 22),
(210, 'Bắc Quang', 22),
(211, 'Đồng Văn', 22),
(212, 'Hà Giang', 22),
(213, 'Hoàng Su Phì', 22),
(214, 'Mèo Vạc', 22),
(215, 'Quản Bạ', 22),
(216, 'Quảng Bình', 22),
(217, 'Vị Xuyên', 22),
(218, 'Xín Mần', 22),
(219, 'Yên Minh', 22),
(220, 'Bình Lục', 23),
(221, 'Duy Tiên', 23),
(222, 'Kim Bảng', 23),
(223, 'Lý Nhân', 23),
(224, 'Phủ Lý', 23),
(225, 'Thanh Liêm', 23),
(226, 'Ba Đình', 24),
(227, 'Cầu Giấy', 24),
(228, 'Đông Anh', 24),
(229, 'Đống Đa', 24),
(230, 'Gia Lâm', 24),
(231, 'Hai Bà Trưng', 24),
(232, 'Hoàn Kiếm', 24),
(233, 'Hoàng Mai', 24),
(234, 'Long Biên', 24),
(235, 'Sóc Sơn', 24),
(236, 'Tây Hồ', 24),
(237, 'Thanh Trì', 24),
(238, 'Thanh Xuân', 24),
(239, 'Từ Liêm', 24),
(240, 'Ba Vì', 24),
(241, 'Chương Mỹ', 24),
(242, 'Đan Phượng', 24),
(243, 'Hà Đông', 24),
(244, 'Hoài Đức', 24),
(245, 'Mỹ Đức', 24),
(246, 'Phú Xuyên', 24),
(247, 'Phúc Thọ', 24),
(248, 'Quốc Oai', 24),
(249, 'Sơn Tây', 24),
(250, 'Thạch Thất', 24),
(251, 'Thanh Oai', 24),
(252, 'Thường Tín', 24),
(253, 'Ứng Hòa', 24),
(254, 'Cẩm Xuyên', 25),
(255, 'Can Lộc', 25),
(256, 'Đức Thọ', 25),
(257, 'Hà Tĩnh', 25),
(258, 'Hồng Lĩnh', 25),
(259, 'Hương Khê', 25),
(260, 'Hương Sơn', 25),
(261, 'Kỳ Anh', 25),
(262, 'Nghi Xuân', 25),
(263, 'Thạch Hà', 25),
(264, 'Vũ Quang', 25),
(265, 'Bình Giang', 26),
(266, 'Cẩm Giàng', 26),
(267, 'Chí Linh', 26),
(268, 'Gia Lộc', 26),
(269, 'Hải Dương', 26),
(270, 'Kim Thành', 26),
(271, 'Kinh Môn', 26),
(272, 'Nam Sách', 26),
(273, 'Ninh Giang', 26),
(274, 'Thanh Hà', 26),
(275, 'Thanh Miện', 26),
(276, 'Tứ Kỳ', 26),
(277, 'An Dương', 27),
(278, 'An Lão', 27),
(279, 'Bạch Long Vĩ', 27),
(280, 'Cát Hải', 27),
(281, 'Đồ Sơn', 27),
(282, 'Hải An', 27),
(283, 'Hồng Bàng', 27),
(284, 'Kiến An', 27),
(285, 'Kiến Thuỵ', 27),
(286, 'Lê Chân', 27),
(287, 'Ngô Quyền', 27),
(288, 'Thủy Nguyên', 27),
(289, 'Tiên Lãng', 27),
(290, 'Vĩnh Bảo', 27),
(291, 'Dương Kinh', 27),
(292, 'Châu Thành', 28),
(293, 'Châu Thành A', 28),
(294, 'Long Mỹ', 28),
(295, 'Phụng Hiệp', 28),
(296, 'Vị Thanh', 28),
(297, 'Vị Thủy', 28),
(298, 'Ngã Bảy', 28),
(299, 'Cao Phong', 29),
(300, 'Đà Bắc', 29),
(301, 'Hòa Bình', 29),
(302, 'Kim Bôi', 29),
(303, 'Kỳ Sơn', 29),
(304, 'Lạc Sơn', 29),
(305, 'Lạc Thủy', 29),
(306, 'Lương Sơn', 29),
(307, 'Mai Châu', 29),
(308, 'Tân Lạc', 29),
(309, 'Yên Thủy', 29),
(310, 'Ân Thi', 30),
(311, 'Hưng Yên', 30),
(312, 'Khoái Châu', 30),
(313, 'Kim Động', 30),
(314, 'Mỹ Hào', 30),
(315, 'Phù Cừ', 30),
(316, 'Tiên Lữ', 30),
(317, 'Văn Giang', 30),
(318, 'Văn Lâm', 30),
(319, 'Yên Mỹ', 30),
(320, 'Cam Lâm', 31),
(321, 'Cam Ranh', 31),
(322, 'Diên Khánh', 31),
(323, 'Khánh Sơn', 31),
(324, 'Khánh Vĩnh', 31),
(325, 'Nha Trang', 31),
(326, 'Ninh Hòa', 31),
(327, 'Trường Sa', 31),
(328, 'Vạn Ninh', 31),
(329, 'An Biên', 32),
(330, 'An Minh', 32),
(331, 'Châu Thành', 32),
(332, 'Giồng Riềng', 32),
(333, 'Gò Quao', 32),
(334, 'Giang Thành', 32),
(335, 'Hà Tiên', 32),
(336, 'Hòn Đất', 32),
(337, 'Kiên Hải', 32),
(338, 'Kiên Lương', 32),
(339, 'Phú Quốc', 32),
(340, 'Rạch Giá', 32),
(341, 'Tân Hiệp', 32),
(342, 'Vĩnh Thuận', 32),
(343, 'U Minh Thượng', 32),
(344, 'Đắk Glei', 33),
(345, 'Đắk Hà', 33),
(346, 'Đắk Tô', 33),
(347, 'Kon Plông', 33),
(348, 'Kon Rẫy', 33),
(349, 'Kon Tum', 33),
(350, 'Ngọc Hồi', 33),
(351, 'Sa Thầy', 33),
(352, 'Tu Mơ Rông', 33),
(353, 'Lai Châu', 34),
(354, 'Mường Tè', 34),
(355, 'Phong Thổ', 34),
(356, 'Sìn Hồ', 34),
(357, 'Tam Đường', 34),
(358, 'Than Uyên', 34),
(359, 'Tân Uyên', 34),
(360, 'Bảo Lâm', 35),
(361, 'Bảo Lộc', 35),
(362, 'Cát Tiên', 35),
(363, 'Đạ Huoai', 35),
(364, 'Đà Lạt', 35),
(365, 'Đạ Tẻh', 35),
(366, 'Đam Rông', 35),
(367, 'Di Linh', 35),
(368, 'Đơn Dương', 35),
(369, 'Đức Trọng', 35),
(370, 'Lạc Dương	', 35),
(371, 'Lâm Hà', 35),
(372, 'Bắc Sơn', 36),
(373, 'Bình Gia', 36),
(374, 'Cao Lộc', 36),
(375, 'Chi Lăng', 36),
(376, 'Đình Lập', 36),
(377, 'Hữu Lũng', 36),
(378, 'Lạng Sơn', 36),
(379, 'Lộc Bình', 36),
(380, 'Tràng Định', 36),
(381, 'Văn Lãng', 36),
(382, 'Văn Quân', 36),
(383, 'Bắc Hà', 37),
(384, 'Bảo Thắng', 37),
(385, 'Bảo Yên', 37),
(386, 'Bát Xát', 37),
(387, 'Lào Cai', 37),
(388, 'Mường Khương', 37),
(389, 'Sa Pa', 37),
(390, 'Si Ma Cai', 37),
(391, 'Văn Bàn', 37),
(392, 'Bến Lức', 38),
(393, 'Cần Đước', 38),
(394, 'Cần Giuộc', 38),
(395, 'Châu Thành', 38),
(396, 'Đức Hòa', 38),
(397, 'Đức Huệ', 38),
(398, 'Mộc Hóa', 38),
(399, 'Tân An', 38),
(400, 'Tân Hưng', 38),
(401, 'Tân Thạnh', 38),
(402, 'Tân Trụ', 38),
(403, 'Thạnh Hóa', 38),
(404, 'Thủ Thừa', 38),
(405, 'Vĩnh Hưng', 38),
(406, 'Giao Thủy', 39),
(407, 'Hải Hậu', 39),
(408, 'Mỹ Lộc', 39),
(409, 'Nam Định', 39),
(410, 'Nam Trực', 39),
(411, 'Nghĩa Hưng', 39),
(412, 'Trực Ninh', 39),
(413, 'Vụ Bản', 39),
(414, 'Xuân Trường', 39),
(415, 'Ý Yên', 39),
(416, 'Anh Sơn', 40),
(417, 'Con Cuông', 40),
(418, 'Cửa Lò', 40),
(419, 'Diễn Châu', 40),
(420, 'Đô Lương', 40),
(421, 'Hưng Nguyên', 40),
(422, 'Kỳ Sơn', 40),
(423, 'Nam Đàn', 40),
(424, 'Nghi Lộc', 40),
(425, 'Nghĩa Đàn', 40),
(426, 'Quế Phong', 40),
(427, 'Quỳnh Lưu', 40),
(428, 'Quỳ Châu', 40),
(429, 'Quỳ Hợp', 40),
(430, 'Tân Kỳ', 40),
(431, 'Thanh Chương', 40),
(432, 'Tương Dương', 40),
(433, 'Vinh', 40),
(434, 'Yên Thành', 40),
(435, 'Thái Hòa', 40),
(436, 'Gia Viễn', 41),
(437, 'Hoa Lư', 41),
(438, 'Kim Sơn', 41),
(439, 'Nho Quan', 41),
(440, 'Ninh Bình', 41),
(441, 'Tam Diệp', 41),
(442, 'Yên Khánh', 41),
(443, 'Yên Mô', 41),
(444, 'Ninh Hải', 42),
(445, 'Ninh Phước', 42),
(446, 'Ninh Sơn', 42),
(447, 'Phan Rang–Tháp Chàm', 42),
(448, 'Thuận Bắc', 42),
(449, 'Thuận Nam', 42),
(450, 'Cẩm Khê', 43),
(451, 'Đoan Hùng', 43),
(452, 'Hạ Hòa', 43),
(453, 'Lâm Thao', 43),
(454, 'Phù Ninh', 43),
(455, 'Phú Thọ', 43),
(456, 'Tam Nông', 43),
(457, 'Tân Sơn', 43),
(458, 'Thanh Ba', 43),
(459, 'Thanh Sơn', 43),
(460, 'Thanh Thủy', 43),
(461, 'Việt Trì', 43),
(462, 'Yên Lập', 43),
(463, 'Đông Hòa', 44),
(464, 'Đồng Xuân', 44),
(465, 'Phú Hòa', 44),
(466, 'Sơn Hòa', 44),
(467, 'Sông Cầu', 44),
(468, 'Sông Hinh', 44),
(469, 'Tây Hòa', 44),
(470, 'Tuy An', 44),
(471, 'Tuy Hòa', 44),
(472, 'Bố Trạch', 45),
(473, 'Đồng Hới', 45),
(474, 'Lệ Thủy', 45),
(475, 'Minh Hóa', 45),
(476, 'Quảng Ninh', 45),
(477, 'Quảng Trạch', 45),
(478, 'Tuyên Hóa', 45),
(479, 'Bắc Trà My', 46),
(480, 'Đại Lộc', 46),
(481, 'Điện Bàn', 46),
(482, 'Đông Giang', 46),
(483, 'Duy Xuyên', 46),
(484, 'Hiệp Đức', 46),
(485, 'Hội An', 46),
(486, 'Nam Giang', 46),
(487, 'Nam Trà My', 46),
(488, 'Núi Thành', 46),
(489, 'Phước Sơn', 46),
(490, 'Quế Sơn', 46),
(491, 'Tam Kỳ', 46),
(492, 'Tây Giang', 46),
(493, 'Thăng Bình', 46),
(494, 'Tiên Phước', 46),
(495, 'Nông Sơn', 46),
(496, 'Ba Tơ', 47),
(497, 'Bình Sơn', 47),
(498, 'Đức Phổ', 47),
(499, 'Lý Sơn', 47),
(500, 'Minh Long', 47),
(501, 'Nghĩa Hành', 47),
(502, 'Quảng Ngãi', 47),
(503, 'Sơn Hà', 47),
(504, 'Sơn Tây', 47),
(505, 'Sơn Tịnh', 47),
(506, 'Tây Trà', 47),
(507, 'Trà Bồng', 47),
(508, 'Tư Nghĩa', 47),
(509, 'Ba Chẽ', 48),
(510, 'Bình Liêu', 48),
(511, 'Cẩm Phả', 48),
(512, 'Cô Tô', 48),
(513, 'Đầm Hà', 48),
(514, 'Đông Triều', 48),
(515, 'Hạ Long', 48),
(516, 'Hải Hà', 48),
(517, 'Hoành Bồ', 48),
(518, 'Móng Cái', 48),
(519, 'Tiên Yên', 48),
(520, 'Uông Bí', 48),
(521, 'Vân Đồn', 48),
(522, 'Yên Hưng', 48),
(523, 'Cam Lộ', 49),
(524, 'Cồn Cỏ', 49),
(525, 'Đa Krông', 49),
(526, 'Đông Hà', 49),
(527, 'Gio Linh', 49),
(528, 'Hải Lăng', 49),
(529, 'Hướng Hóa', 49),
(530, 'Quảng Trị', 49),
(531, 'Triệu Phong', 49),
(532, 'Vĩnh Linh', 49),
(533, 'Châu Thành', 50),
(534, 'Cù Lao Dung', 50),
(535, 'Kế Sách', 50),
(536, 'Long Phú', 50),
(537, 'Mỹ Tú', 50),
(538, 'Mỹ Xuyên', 50),
(539, 'Sóc Trăng', 50),
(540, 'Thạnh Trị', 50),
(541, 'Vĩnh Châu', 50),
(542, 'Bắc Yên', 51),
(543, 'Mai Sơn', 51),
(544, 'Mộc Châu', 51),
(545, 'Mường La', 51),
(546, 'Phù Yên', 51),
(547, 'Quỳnh Nhai', 51),
(548, 'Sơn La', 51),
(549, 'Sông Mã', 51),
(550, 'Sốp Cộp', 51),
(551, 'Thuận Châu', 51),
(552, 'Yên Châu', 51),
(553, 'Bến Cầu', 52),
(554, 'Châu Thành', 52),
(555, 'Dương Minh Châu', 52),
(556, 'Gò Dầu', 52),
(557, 'Hòa Thành', 52),
(558, 'Tân Biên', 52),
(559, 'Tân Châu', 52),
(560, 'Tây Ninh', 52),
(561, 'Trảng Bàng', 52),
(562, 'Đông Hưng', 53),
(563, 'Hưng Hà', 53),
(564, 'Kiến Xương', 53),
(565, 'Quỳnh Phụ', 53),
(566, 'Thái Bình', 53),
(567, 'Thái Thụy', 53),
(568, 'Tiền Hải', 53),
(569, 'Vũ Thư', 53),
(570, 'Đại Từ', 54),
(571, 'Định Hóa', 54),
(572, 'Đồng Hỷ', 54),
(573, 'Phổ Yên', 54),
(574, 'Phú Bình', 54),
(575, 'Phú Lương', 54),
(576, 'Sông Công', 54),
(577, 'Thái Nguyên', 54),
(578, 'Võ Nhai', 54),
(579, 'Bá Thước', 55),
(580, 'Bỉm Sơn', 55),
(581, 'Cẩm Thủy', 55),
(582, 'Đông Sơn', 55),
(583, 'Hà Trung', 55),
(584, 'Hậu Lộc', 55),
(585, 'Hoằng Hóa', 55),
(586, 'Lang Chánh', 55),
(587, 'Mường Lát', 55),
(588, 'Ngọc Lặc', 55),
(589, 'Như Thanh', 55),
(590, 'Như Xuân', 55),
(591, 'Nông Cống', 55),
(592, 'Quan Hóa', 55),
(593, 'Quan Sơn', 55),
(594, 'Quảng Xương', 55),
(595, 'Sầm Sơn', 55),
(596, 'Thạch Thành', 55),
(597, 'Thanh Hóa', 55),
(598, 'Thiệu Hóa', 55),
(599, 'Thọ Xuân', 55),
(600, 'Thường Xuân', 55),
(601, 'Tĩnh Gia', 55),
(602, 'Triệu Sơn', 55),
(603, 'Vĩnh Lộc', 55),
(604, 'Yên Định', 55),
(605, 'A Lưới', 56),
(606, 'Huế', 56),
(607, 'Hương Thủy', 56),
(608, 'Hương Trà', 56),
(609, 'Nam Đông', 56),
(610, 'Phong Điền', 56),
(611, 'Phú Lộc', 56),
(612, 'Phú Vang', 56),
(613, 'Quảng Điền', 56),
(614, 'Cái Bè', 57),
(615, 'Cai Lậy', 57),
(616, 'Châu Thành', 57),
(617, 'Chợ Gạo', 57),
(618, 'Gò Công', 57),
(619, 'Gò Công Dông', 57),
(620, 'Gò Công Tây', 57),
(621, 'Mỹ Tho', 57),
(622, 'Tân Phước', 57),
(623, 'Bình Chánh', 58),
(624, 'Bình Tân', 58),
(625, 'Bình Thạnh', 58),
(626, 'Cần Giờ', 58),
(627, 'Củ Chi', 58),
(628, 'Quận 1', 58),
(629, 'Quận 2', 58),
(630, 'Quận 3', 58),
(631, 'Quận 4', 58),
(632, 'Quận 5', 58),
(633, 'Quận 6', 58),
(634, 'Quận 7', 58),
(635, 'Quận 8', 58),
(636, 'Quận 9', 58),
(637, 'Quận 10', 58),
(638, 'Quận 11', 58),
(639, 'Quận 12', 58),
(640, 'Gò Vấp', 58),
(641, 'Hóc Môn', 58),
(642, 'Nhà Bè', 58),
(643, 'Phú Nhuận', 58),
(644, 'Tân Bình', 58),
(645, 'Tân Phú', 58),
(646, 'Thủ Đức', 58),
(647, 'Càng Long', 59),
(648, 'Cầu Kè', 59),
(649, 'Cầu Ngang', 59),
(650, 'Châu Thành', 59),
(651, 'Duyên Hải', 59),
(652, 'Tiểu Cần', 59),
(653, 'Trà Cú', 59),
(654, 'Trà Vinh', 59),
(655, 'Chiêm Hóa', 60),
(656, 'Hàm Yên', 60),
(657, 'Nà Hang', 60),
(658, 'Sơn Dương', 60),
(659, 'Tuyên Quang', 60),
(660, 'Yên Sơn', 60),
(661, 'Bình Minh', 61),
(662, 'Bình Tân', 61),
(663, 'Long Hồ', 61),
(664, 'Mang Thít', 61),
(665, 'Tâm Bình', 61),
(666, 'Trà Ôn', 61),
(667, 'Vĩnh Long', 61),
(668, 'Vũng Liêm', 61),
(669, 'Bình Xuyên', 62),
(670, 'Lập Thạch', 62),
(671, 'Phúc Yên', 62),
(672, 'Tam Đảo', 62),
(673, 'Tam Dương', 62),
(674, 'Vĩnh Tường', 62),
(675, 'Vĩnh Yên', 62),
(676, 'Yên Lạc', 62),
(677, 'Lục Yên', 63),
(678, 'Mù Cang Chải', 63),
(679, 'Nghĩa Lộ', 63),
(680, 'Trạm Tấu', 63),
(681, 'Trấn Yên', 63),
(682, 'Văn Chấn', 63),
(683, 'Văn Yên', 63),
(684, 'Yên Bái', 63),
(685, 'Yên Bình', 63);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_locations`
--

CREATE TABLE `tbl_locations` (
  `LOCATION_ID` int(11) NOT NULL,
  `LOCATION_NAME` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ADDRESS` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `LATITUDE` varchar(10) DEFAULT NULL,
  `LONGITUDE` varchar(10) DEFAULT NULL,
  `DISTRICT_ID` int(11) NOT NULL,
  `DATE_CREATED` datetime DEFAULT CURRENT_TIMESTAMP,
  `DATE_UPDATED` datetime NOT NULL,
  `STATUS` int(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_locations`
--

INSERT INTO `tbl_locations` (`LOCATION_ID`, `LOCATION_NAME`, `ADDRESS`, `LATITUDE`, `LONGITUDE`, `DISTRICT_ID`, `DATE_CREATED`, `DATE_UPDATED`, `STATUS`) VALUES
(1, 'Trần Quang Diệu', '31 Trần Quang Diệu, P.13, Q.3, HCM, VN', '10.783693', '106.67848 ', 630, '2016-11-14 09:42:00', '0000-00-00 00:00:00', 1);

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
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `USER_ID` int(11) NOT NULL,
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `GENDER` tinyint(1) DEFAULT NULL,
  `PHONE` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `LOGIN_ID` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `AUTHID` tinyint(10) NOT NULL,
  `ISFILLEDTOCONFIRM` tinyint(1) NOT NULL DEFAULT '0',
  `CREATEDAT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATEDAT` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_vouchers`
--

CREATE TABLE `tbl_vouchers` (
  `VOUCHER_ID` int(11) NOT NULL,
  `VOUCHER` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `PRICE` decimal(10,0) NOT NULL,
  `CREATEDAT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATEDAT` datetime NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_vouchers`
--

INSERT INTO `tbl_vouchers` (`VOUCHER_ID`, `VOUCHER`, `PRICE`, `CREATEDAT`, `UPDATEDAT`, `ACTIVE`) VALUES
(1, 'ECO Booking', '9000000', '2016-11-14 21:36:12', '0000-00-00 00:00:00', 1),
(2, 'VIP Booking', '18000000', '2016-11-14 21:36:27', '0000-00-00 00:00:00', 1);

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
(7, 'Sunday'),
(11, 'Testing');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD PRIMARY KEY (`APPOINTMENT_ID`),
  ADD KEY `CUSTOMER_ID` (`CUSTOMER_ID`),
  ADD KEY `LOCATION_ID` (`LOCATION_ID`),
  ADD KEY `VOUCHER_ID` (`VOUCHER_ID`);

--
-- Indexes for table `tbl_appointmentschedule`
--
ALTER TABLE `tbl_appointmentschedule`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `TIME_ID` (`TIME_ID`),
  ADD KEY `DAY_ID` (`DAY_ID`),
  ADD KEY `APPOINTMENT_ID` (`APPOINTMENT_ID`);

--
-- Indexes for table `tbl_cities`
--
ALTER TABLE `tbl_cities`
  ADD PRIMARY KEY (`CITY_ID`),
  ADD KEY `COUNTRY_ID` (`COUNTRY_ID`),
  ADD KEY `COUNTRY_ID_2` (`COUNTRY_ID`);

--
-- Indexes for table `tbl_countries`
--
ALTER TABLE `tbl_countries`
  ADD PRIMARY KEY (`COUNTRY_ID`);

--
-- Indexes for table `tbl_customers`
--
ALTER TABLE `tbl_customers`
  ADD PRIMARY KEY (`CUSTOMER_ID`),
  ADD UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`);

--
-- Indexes for table `tbl_districts`
--
ALTER TABLE `tbl_districts`
  ADD PRIMARY KEY (`DISTRICT_ID`),
  ADD KEY `CITY_ID` (`CITY_ID`);

--
-- Indexes for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  ADD PRIMARY KEY (`LOCATION_ID`),
  ADD UNIQUE KEY `ID_UNIQUE` (`LOCATION_ID`),
  ADD KEY `DISTRICT_ID` (`DISTRICT_ID`);

--
-- Indexes for table `tbl_time`
--
ALTER TABLE `tbl_time`
  ADD PRIMARY KEY (`TIME_ID`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`USER_ID`),
  ADD UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`),
  ADD UNIQUE KEY `AUTHID` (`AUTHID`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- Indexes for table `tbl_vouchers`
--
ALTER TABLE `tbl_vouchers`
  ADD PRIMARY KEY (`VOUCHER_ID`);

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
  MODIFY `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbl_appointmentschedule`
--
ALTER TABLE `tbl_appointmentschedule`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `tbl_cities`
--
ALTER TABLE `tbl_cities`
  MODIFY `CITY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;
--
-- AUTO_INCREMENT for table `tbl_countries`
--
ALTER TABLE `tbl_countries`
  MODIFY `COUNTRY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;
--
-- AUTO_INCREMENT for table `tbl_customers`
--
ALTER TABLE `tbl_customers`
  MODIFY `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tbl_districts`
--
ALTER TABLE `tbl_districts`
  MODIFY `DISTRICT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=686;
--
-- AUTO_INCREMENT for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  MODIFY `LOCATION_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tbl_time`
--
ALTER TABLE `tbl_time`
  MODIFY `TIME_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_vouchers`
--
ALTER TABLE `tbl_vouchers`
  MODIFY `VOUCHER_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_weekdays`
--
ALTER TABLE `tbl_weekdays`
  MODIFY `DAY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD CONSTRAINT `tbl_appointments_ibfk_3` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `tbl_customers` (`CUSTOMER_ID`),
  ADD CONSTRAINT `tbl_appointments_ibfk_4` FOREIGN KEY (`VOUCHER_ID`) REFERENCES `tbl_vouchers` (`VOUCHER_ID`),
  ADD CONSTRAINT `tbl_appointments_ibfk_5` FOREIGN KEY (`LOCATION_ID`) REFERENCES `tbl_locations` (`LOCATION_ID`);

--
-- Constraints for table `tbl_appointmentschedule`
--
ALTER TABLE `tbl_appointmentschedule`
  ADD CONSTRAINT `tbl_appointmentschedule_ibfk_1` FOREIGN KEY (`APPOINTMENT_ID`) REFERENCES `tbl_appointments` (`APPOINTMENT_ID`),
  ADD CONSTRAINT `tbl_appointmentschedule_ibfk_2` FOREIGN KEY (`TIME_ID`) REFERENCES `tbl_time` (`TIME_ID`),
  ADD CONSTRAINT `tbl_appointmentschedule_ibfk_3` FOREIGN KEY (`DAY_ID`) REFERENCES `tbl_weekdays` (`DAY_ID`);

--
-- Constraints for table `tbl_cities`
--
ALTER TABLE `tbl_cities`
  ADD CONSTRAINT `tbl_cities_ibfk_1` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `tbl_countries` (`COUNTRY_ID`);

--
-- Constraints for table `tbl_districts`
--
ALTER TABLE `tbl_districts`
  ADD CONSTRAINT `tbl_districts_ibfk_1` FOREIGN KEY (`CITY_ID`) REFERENCES `tbl_cities` (`CITY_ID`);

--
-- Constraints for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  ADD CONSTRAINT `tbl_locations_ibfk_1` FOREIGN KEY (`DISTRICT_ID`) REFERENCES `tbl_districts` (`DISTRICT_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
