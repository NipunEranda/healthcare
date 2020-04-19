-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 19, 2020 at 08:17 PM
-- Server version: 5.7.29-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healthcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `appId` int(11) NOT NULL,
  `appNo` varchar(255) NOT NULL,
  `appDate` varchar(255) NOT NULL,
  `appType` varchar(255) NOT NULL,
  `appDesc` varchar(255) NOT NULL,
  `appDoctor` varchar(255) NOT NULL,
  `appHospital` varchar(255) NOT NULL,
  `appPatient` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`appId`, `appNo`, `appDate`, `appType`, `appDesc`, `appDoctor`, `appHospital`, `appPatient`) VALUES
(1, 'no1', '2020-01-02', 'reservation', 'this is a test', 'John', 'hemas', 'Sam');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `Did` int(12) NOT NULL,
  `idnum` varchar(65) NOT NULL,
  `workplace` varchar(80) NOT NULL,
  `degree` varchar(34) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`Did`, `idnum`, `workplace`, `degree`) VALUES
(4, '7523652244215v', 'Hospital', 'Hons');

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

CREATE TABLE `hospital` (
  `HosCode` int(11) NOT NULL,
  `Hid` varchar(11) NOT NULL,
  `Hname` varchar(20) NOT NULL,
  `address` varchar(20) NOT NULL,
  `telephone` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`HosCode`, `Hid`, `Hname`, `address`, `telephone`) VALUES
(2, '1', 'hemas', 'maharagama', '01185426'),
(7, '2', 'jaya', 'maharagama', '011854695');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `Login_Id` int(255) NOT NULL,
  `Login_Role` int(11) NOT NULL,
  `Login_Email` varchar(255) NOT NULL,
  `Login_Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`Login_Id`, `Login_Role`, `Login_Email`, `Login_Password`) VALUES
(1, 1, 'admin@gmail.com', '1234'),
(2, 2, 'isira@gmail.com', '1234'),
(3, 2, 'nadeera@gmail.com', '1234'),
(4, 3, 'sunil@gmail.com', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `userId` int(11) NOT NULL,
  `hospitalId` int(11) DEFAULT NULL,
  `patientCondition` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`userId`, `hospitalId`, `patientCondition`) VALUES
(2, 1, 'Diabetes'),
(3, 2, 'osteoporosis');

-- --------------------------------------------------------

--
-- Table structure for table `payment_details`
--

CREATE TABLE `payment_details` (
  `paymentId` varchar(10) NOT NULL,
  `paidAmount` int(10) NOT NULL,
  `paymentDescription` varchar(50) NOT NULL,
  `Hid` int(11) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment_details`
--

INSERT INTO `payment_details` (`paymentId`, `paidAmount`, `paymentDescription`, `Hid`, `userId`) VALUES
('P0001', 50000, '10 days in hospital', 1, 2),
('P0002', 21000, 'Dead in hospital after 1 week', 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `roleName` varchar(255) NOT NULL,
  `role_desc` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `roleName`, `role_desc`) VALUES
(1, 'admin', 'A person who has highest privillege in the system'),
(2, 'user', 'People who login to the system. Specially Patients'),
(3, 'doctor', 'Doctor who treats people in the hospital');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `loginId` int(11) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `mobileNumber` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `loginId`, `firstName`, `lastName`, `age`, `gender`, `address`, `mobileNumber`) VALUES
(1, 1, 'Nipun', 'Amarasekara', 22, 'Male', 'Colombo3', '0712365485'),
(2, 2, 'Isira', 'Nelaka', 23, 'Male', 'Colombo', '0771956231'),
(3, 3, 'Nadeera', 'Amarasekara', 26, 'Female', 'Colombo', '0752369856'),
(4, 4, 'Sunil', 'Karunarathna', 45, 'Male', 'Colombo', '0782536956');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`appId`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`Did`);

--
-- Indexes for table `hospital`
--
ALTER TABLE `hospital`
  ADD PRIMARY KEY (`HosCode`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`Login_Id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD PRIMARY KEY (`paymentId`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hospital`
--
ALTER TABLE `hospital`
  MODIFY `HosCode` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `Login_Id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
