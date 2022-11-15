
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tnt
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tnt` DEFAULT CHARACTER SET utf8 ;
USE `tnt` ;

-- -----------------------------------------------------
-- Table `tnt`.`eventcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tnt`.`eventcategory` (
  `eventCategoryId` INT NOT NULL auto_increment,
  `eventCategoryName` VARCHAR(100) NOT NULL UNIQUE,
  `eventCategoryDescription` VARCHAR(500) NULL DEFAULT NULL,
  `eventDuration` INT NOT NULL,
  PRIMARY KEY (`eventCategoryId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `tnt`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tnt`.`event` (
  `eventId` INT NOT NULL auto_increment,
  `bookingName` VARCHAR(100) NOT NULL,
  `bookingEmail` VARCHAR(45) NOT NULL,
  `eventCategory` VARCHAR(45) NOT NULL,
  `eventStartTime` DATETIME NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventNotes` VARCHAR(500) NULL DEFAULT NULL,
  `eventCategory_eventCategoryId` INT NOT NULL,
  PRIMARY KEY (`eventId`),
  INDEX `fk_event_eventCategory_idx` (`eventCategory_eventCategoryId` ASC) VISIBLE,
  CONSTRAINT `fk_event_eventCategory`
    FOREIGN KEY (`eventCategory_eventCategoryId`)
    REFERENCES `tnt`.`eventcategory` (`eventCategoryId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET NAMES "UTF8";
insert into eventCategory (eventCategoryId,eventCategoryName,eventCategoryDescription,eventDuration) 
values (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',30);

insert into eventCategory (eventCategoryId,eventCategoryName,eventCategoryDescription,eventDuration) 
values (2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20);

insert into eventCategory (eventCategoryId,eventCategoryName,eventCategoryDescription,eventDuration) 
values (3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);

insert into eventCategory (eventCategoryId,eventCategoryName,eventCategoryDescription,eventDuration) 
values (4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);

insert into eventCategory (eventCategoryId,eventCategoryName,eventCategoryDescription,eventDuration) 
values (5,'Server-side Clinic',null,30);

insert into event (eventId,bookingName,bookingEmail,eventCategory,eventStartTime,eventDuration,eventNotes,eventCategory_eventCategoryId)
values (1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.ac.th','DevOps/Infra Clinic','22-05-23 13:30',30,null,2);

insert into event (eventId,bookingName,bookingEmail,eventCategory,eventStartTime,eventDuration,eventNotes,eventCategory_eventCategoryId)
values (2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.ac.th','Project Management Clinic','22-04-27 9:30',30,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',1);

insert into event (eventId,bookingName,bookingEmail,eventCategory,eventStartTime,eventDuration,eventNotes,eventCategory_eventCategoryId)
values (3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th','Database Clinic','22-05-23 16:30',15,null,3);

CREATE TABLE IF NOT EXISTS tnt.user (
  userId INT NOT NULL auto_increment,
  userName VARCHAR(100) NOT NULL UNIQUE ,
  userEmail VARCHAR(50) NOT NULL UNIQUE,
  role enum('admin','lecturer','student') NOT NULL DEFAULT 'student',
  createdOn timestamp NOT NULL default current_timestamp,
  updatedOn timestamp NOT NULL default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (userId))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

insert into user(userId,userName,userEmail,role) values (1,'OASIP ADMIN','oasip.admin@kmutt.ac.th','admin');
insert into user(userId,userName,userEmail,role) values (2,'Somchai Jaidee','somchai.jai@kmutt.ac.th','lecturer');
insert into user(userId,userName,userEmail,role) values (3,'Komkrid Rakdee','komkrid.rak@mail.kmutt.ac.th','student');
insert into user(userId,userName,userEmail,role) values (4,'สมเกียรติ ขยันเรียน','somkiat.kay@kmutt.ac.th','student');
