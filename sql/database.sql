-- MySQL Script generated by MySQL Workbench
-- Wed Sep 13 11:47:25 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema swp391
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `swp391` ;

-- -----------------------------------------------------
-- Schema swp391
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `swp391` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema swp391
-- -----------------------------------------------------
USE `swp391` ;

-- -----------------------------------------------------
-- Table `swp391`.`setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`setting` ;

CREATE TABLE IF NOT EXISTS `swp391`.`setting` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `type_id` INT NULL,
                                                  `setting_title` VARCHAR(45) NULL,
    `description` LONGTEXT NULL,
    `status` BIT(1) NULL DEFAULT 1,
    `display_order` INT ,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`user` ;

CREATE TABLE IF NOT EXISTS `swp391`.`user` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `email` VARCHAR(45) NULL,
    `phone` VARCHAR(45) NULL,
    `password` VARCHAR(45) NULL,
    `status` BIT(1) NULL DEFAULT 1,
    `note` VARCHAR(255),
    `full_name` VARCHAR(255) NULL,
    `avatar_url` TEXT NULL,
    `role_id` INT NULL,
    `token` varchar(255) DEFAULT NULL,
    `active` BIT(1) NULL DEFAULT 0,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `a_idx` (`role_id` ASC) VISIBLE,
    FOREIGN KEY (`role_id`)
    REFERENCES `swp391`.`setting` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`subject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`subject` ;

CREATE TABLE IF NOT EXISTS `swp391`.`subject` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `subject_manager_id` INT NULL,
                                                  `subject_name` VARCHAR(255) NULL,
    `subject_code` VARCHAR(45) NULL,
    `status` BIT(1) NULL DEFAULT 1,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `subject_manager_id_idx` (`subject_manager_id` ASC) VISIBLE,
    FOREIGN KEY (`subject_manager_id`)
    REFERENCES `swp391`.`user` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`assignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`assignment` ;

CREATE TABLE IF NOT EXISTS `swp391`.`assignment` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `subject_id` INT NULL,
                                                     `title` VARCHAR(45) NULL,
    `status` BIT(1) NULL DEFAULT 1,
    `description` VARCHAR(245) NULL,
    `is_subject_assignment` BIT(1) NULL,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `a_idx` (`subject_id` ASC) VISIBLE,
    FOREIGN KEY (`subject_id`)
    REFERENCES `swp391`.`subject` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`class` ;

CREATE TABLE IF NOT EXISTS `swp391`.`class` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `class_name` VARCHAR(245) NULL,
    `description` LONGTEXT NULL,
    `subject_id` INT NULL,
    `semester_id` INT NULL,
    `teacher_id` INT NULL,
    `status` BIT(1) NULL,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX ` a_idx` (`teacher_id` ASC) VISIBLE,
    INDEX `b_idx` (`semester_id` ASC) VISIBLE,
    FOREIGN KEY (`subject_id`)
    REFERENCES `swp391`.`subject` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION,
    FOREIGN KEY (`teacher_id`)
    REFERENCES `swp391`.`user` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION,
    FOREIGN KEY (`semester_id`)
    REFERENCES `swp391`.`setting` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`class_assignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`class_assignment` ;

CREATE TABLE IF NOT EXISTS `swp391`.`class_assignment` (
                                                           `id` INT NOT NULL AUTO_INCREMENT,
                                                           `class_id` INT NULL,
                                                           `assignment_id` INT NULL,
                                                           `start_date` DATETIME NULL,
                                                           `end_date` DATETIME NULL,
                                                           `status` BIT(1) NULL,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `a_idx` (`class_id` ASC) VISIBLE,
    INDEX `b_idx` (`assignment_id` ASC) VISIBLE,
    FOREIGN KEY (`class_id`)
    REFERENCES `swp391`.`class` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION,
    FOREIGN KEY (`assignment_id`)
    REFERENCES `swp391`.`assignment` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`class_issue_setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`class_issue_setting` ;

CREATE TABLE IF NOT EXISTS `swp391`.`class_issue_setting` (
                                                              `id` INT NOT NULL AUTO_INCREMENT,
                                                              `class_id` INT NULL,
                                                              `type` INT NULL,
                                                              `status` BIT(1) NULL,
    `work_processes` VARCHAR(155) NULL,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `a_idx` (`class_id` ASC) VISIBLE,
    FOREIGN KEY (`class_id`)
    REFERENCES `swp391`.`class` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`project` ;

CREATE TABLE IF NOT EXISTS `swp391`.`project` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `class_id` INT NULL,
                                                  `project_mentor_id` INT NULL,
                                                  `team_leader_id` INT NULL,
                                                  `title` VARCHAR(45) NULL,
    `status` BIT(1) NULL,
    `group_name` VARCHAR(45) NULL,
    `description` VARCHAR(200) NULL,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `a_idx` (`class_id` ASC) VISIBLE,
    INDEX `a_idx1` (`project_mentor_id` ASC) VISIBLE,
    INDEX `a_idx2` (`team_leader_id` ASC) VISIBLE,
    FOREIGN KEY (`class_id`)
    REFERENCES `swp391`.`class` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION,
    FOREIGN KEY (`project_mentor_id`)
    REFERENCES `swp391`.`user` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION,
    FOREIGN KEY (`team_leader_id`)
    REFERENCES `swp391`.`user` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swp391`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`student_class` ;

CREATE TABLE IF NOT EXISTS `swp391`.`student_class` (
                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                        `student_id` INT NOT NULL,
                                                        `class_id` INT NOT NULL,
                                                        `project_id` INT NULL,
                                                        `create_by` INT NULL DEFAULT 0,
                                                        `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
                                                        `update_by` INT NULL DEFAULT 0,
                                                        `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
                                                        PRIMARY KEY (`id`, `student_id`, `class_id`),
    INDEX `b_idx` (`class_id` ASC) VISIBLE,
    INDEX `a_idx` (`project_id` ASC) VISIBLE,
    FOREIGN KEY (`student_id`)
    REFERENCES `swp391`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`class_id`)
    REFERENCES `swp391`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`project_id`)
    REFERENCES `swp391`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `swp391`.`subject_setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swp391`.`subject_setting` ;

CREATE TABLE IF NOT EXISTS `swp391`.`subject_setting` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `subject_id` INT NULL,
                                                          `type_id` INT NULL,
                                                          `setting_title` VARCHAR(45) NULL,
    `setting_value` VARCHAR(45) NULL,
    `status` BIT(1) NULL DEFAULT 1,
    `display_order` INT ,
    `create_by` INT NULL DEFAULT 0,
    `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_by` INT NULL DEFAULT 0,
    `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`subject_id`)
    REFERENCES `swp391`.`subject` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


---------------------------------------- INSERT DATA ----------------------------------------
-- 1 = role
-- 2 = mail domain
-- 3 = semester

INSERT INTO setting(type_id,setting_title,description,display_order)
VALUES
    (1, "Student","Learner in an educational institution",4),
    (1, "Admin","A person who manages and oversees administrative tasks to keep things running smoothly in an organization or system",1),
    (1, "Subject manager","An individual who manages the curriculum and resources related to specific academic subjects",2),
    (1, "Teacher","a=An educator who imparts knowledge, skills, and guidance to students in a classroom or educational setting",3),
    (2, "gmail.com","Normal gmail",1),
    (2, "fpt.edu.vn","Gmail of FPT University",2),
    (3, "SUMMER 23","Semester start from May to October in 2023",1),
    (3, "FALL 23","Semester start from September to December in 2023",2);

-- admin
INSERT INTO `user` (`email`,`phone`,`password`,`full_name`,`role_id`, `active`, `avatar_url`)
VALUES
    ("admin@gmail.com","0999999999","21232f297a57a5a743894a0e4a801fc3","Admin",2,1,"/images/user_icon.png"),
    ("subject@gmail.com","0999999998","c4ca4238a0b923820dcc509a6f75849b","Subject manager",3,1,"/images/user_icon.png"),
    ("class@gmail.com","0999999997","c4ca4238a0b923820dcc509a6f75849b","Class manager",4,1,"/images/user_icon.png"),
    ("leader@gmail.com","0999999997","c4ca4238a0b923820dcc509a6f75849b","Team leader",1,1,"/images/user_icon.png"),
    ("mentor@gmail.com","0999999997","c4ca4238a0b923820dcc509a6f75849b","Project mentor",4,1,"/images/user_icon.png");

-- user
INSERT INTO `user` (`email`,`phone`,`password`,`full_name`,`avatar_url`,`role_id`, `active`)
VALUES
    ("julianlester@gmail.com","0027829656","c4ca4238a0b923820dcc509a6f75849b","Julian Lester","/images/user_icon.png",4,1),
    ("galvinbass4030@gmail.com","0037963572","c4ca4238a0b923820dcc509a6f75849b","Galvin Bass","/images/user_icon.png",1,1),
    ("brianmassey@gmail.com","0436285872","c4ca4238a0b923820dcc509a6f75849b","Brian Massey","/images/user_icon.png",1,1),
    ("judahcardenas5324@gmail.com","0681589922","c4ca4238a0b923820dcc509a6f75849b","Judah Cardenas","/images/user_icon.png",1,1),
    ("kellyreyes9226@gmail.com","0363517319","c4ca4238a0b923820dcc509a6f75849b","Kelly Reyes","/images/user_icon.png",1,1),
    ("kevinwilliam@gmail.com","0905521148","c4ca4238a0b923820dcc509a6f75849b","Kevin William","/images/user_icon.png",1,1),
    ("lesleycastro@gmail.com","0571427370","c4ca4238a0b923820dcc509a6f75849b","Lesley Castro","/images/user_icon.png",1,1),
    ("danarosario@gmail.com","0272326964","c4ca4238a0b923820dcc509a6f75849b","Dana Rosario","/images/user_icon.png",1,1),
    ("aimeeewing@gmail.com","0636454167","c4ca4238a0b923820dcc509a6f75849b","Aimee Ewing","/images/user_icon.png",1,1),
    ("amywalton@gmail.com","0858486104","c4ca4238a0b923820dcc509a6f75849b","Amy Walton","/images/user_icon.png",1,1);
INSERT INTO `user` (`email`,`phone`,`password`,`full_name`,`avatar_url`,`role_id`, `active`)
VALUES
    ("rashadrush2211@gmail.com","0860113768","c4ca4238a0b923820dcc509a6f75849b","Rashad Rush","/images/user_icon.png",1,1),
    ("elainelawrence@gmail.com","0721721061","c4ca4238a0b923820dcc509a6f75849b","Elaine Lawrence","/images/user_icon.png",1,1),
    ("larissareese@gmail.com","0279994112","c4ca4238a0b923820dcc509a6f75849b","Larissa Reese","/images/user_icon.png",1,1),
    ("holleemyers@gmail.com","0532678220","c4ca4238a0b923820dcc509a6f75849b","Hollee Myers","/images/user_icon.png",1,1),
    ("xanthusmcfadden3684@gmail.com","0302569530","c4ca4238a0b923820dcc509a6f75849b","Xanthus Mcfadden","/images/user_icon.png",1,1),
    ("mylesdavidson@gmail.com","0583590348","c4ca4238a0b923820dcc509a6f75849b","Myles Davidson","/images/user_icon.png",1,1),
    ("teegansantana@gmail.com","0547146642","c4ca4238a0b923820dcc509a6f75849b","Teegan Santana","/images/user_icon.png",1,1),
    ("elvisratliff@gmail.com","0911884338","c4ca4238a0b923820dcc509a6f75849b","Elvis Ratliff","/images/user_icon.png",1,1),
    ("rowaningram@gmail.com","0734547525","c4ca4238a0b923820dcc509a6f75849b","Rowan Ingram","/images/user_icon.png",1,1),
    ("oraallen@gmail.com","0298393485","c4ca4238a0b923820dcc509a6f75849b","Ora Allen","/images/user_icon.png",1,1);
INSERT INTO `user` (`email`,`phone`,`password`,`full_name`,`avatar_url`,`role_id`, `active`)
VALUES
    ("a@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","a","/images/user_icon.png",1,1),
    ("b@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","b","/images/user_icon.png",1,1),
    ("c@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","c","/images/user_icon.png",1,1),
    ("d@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","d","/images/user_icon.png",1,1),
    ("e@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","e","/images/user_icon.png",1,1),
    ("f@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","f","/images/user_icon.png",1,1),
    ("g@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","g","/images/user_icon.png",1,1),
    ("h@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","h","/images/user_icon.png",1,1),
    ("i@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","i","/images/user_icon.png",1,1),
    ("k@gmail.com",NULL,"c4ca4238a0b923820dcc509a6f75849b","k","/images/user_icon.png",1,1);

-- subject
INSERT INTO `subject` (`subject_manager_id`,`subject_name`,`subject_code`)
VALUES
    (2,"Software development project","SWP391"),
    (2,"Java Web Application Development","PRJ301"),
    (2,"Software Requirement","SWR302"),
    (4,"Software Testing","SWT301"),
    (4,"Basic Cross-Platform Application Programming With .NET","PRN211"),
    (4,"Front-End web development with React","FER201m");

-- subject_setting
INSERT INTO subject_setting(subject_id,type_id,setting_title,setting_value,display_order)
VALUES
    (1 ,1, "High","240",1),
    (1 ,1, "Medium","120",2),
    (1 ,1, "Low","60",3),
    (1 ,2, "High",">=80%",1),
    (1 ,2, "Medium",">=50%",2),
    (1 ,2, "Low","<50%",3),
    (2 ,1, "High","240",1),
    (2 ,1, "Medium","120",2),
    (2 ,2, "High",">=80%",1),
    (2 ,2, "Medium",">=50%",2),
    (2 ,2, "Low","<50%",3);

INSERT INTO `class` (`class_name`,`description`,`subject_id`,`semester_id`,`teacher_id`,`status`)
VALUES
    ("SE1720","Study software engineering",1,7,3,1),
    ("SE1722","Study software engineering",1,7,3,1),
    ("SE1704","Study software engineering",1,7,3,1),
    ("SE1707","Study software engineering",1,7,3,1),
    ("SE1712","Study software engineering",2,7,6,1),
    ("SE1715","Study software engineering",2,7,6,1),
    ("SE1709","Study software engineering",2,7,6,1),
    ("SE1710","Study software engineering",2,7,6,1),
    ("SE1740","Study software engineering",1,8,6,1),
    ("SE1741","Study software engineering",1,8,6,1),
    ("SE1731","Study software engineering",1,8,6,1),
    ("SE1736","Study software engineering",1,8,6,1),
    ("SE1740","Study software engineering",2,8,3,1),
    ("SE1745","Study software engineering",2,8,3,1),
    ("SE1734","Study software engineering",2,8,3,1),
    ("SE1736","Study software engineering",2,8,3,1);

INSERT INTO project (class_id, project_mentor_id, team_leader_id, title, status,group_name,description)
VALUES
    (1, 5, 1, "Project A", 0, "Group A", "Web app with Servlet/JSP and Mysql"),
    (1, 5, 6, "Project B", 0, "Group B", "Web app with Servlet/JSP and Mysql"),
    (1, 5, 11, "Project C", 0, "Group C", "Web app with Servlet/JSP and Mysql"),
    (1, 5, 16, "Project D", 0, "Group D", "Web app with Servlet/JSP and Mysql"),
    (1, 5, 21, "Project E", 0, "Group E", "Web app with Servlet/JSP and Mysql"),
    (1, 5, 26, "Project F", 0, "Group F", "Web app with Servlet/JSP and Mysql");

INSERT INTO student_class (student_id, class_id, project_id)
VALUES
    (6, 1, 1),    -- Student 6 in Project 1
    (7, 1, 1),    -- Student 7 in Project 1
    (8, 1, 1),    -- Student 8 in Project 1
    (9, 1, 1),    -- Student 9 in Project 1
    (10, 1, 1),   -- Student 10 in Project 1
    (11, 1, 2),   -- Student 11 in Project 2
    (12, 1, 2),   -- Student 12 in Project 2
    (13, 1, 2),   -- Student 13 in Project 2
    (14, 1, 2),   -- Student 14 in Project 2
    (15, 1, 2),   -- Student 15 in Project 2
    (16, 1, 3),   -- Student 16 in Project 3
    (17, 1, 3),   -- Student 17 in Project 3
    (18, 1, 3),   -- Student 18 in Project 3
    (19, 1, 3),   -- Student 19 in Project 3
    (20, 1, 3),   -- Student 20 in Project 3
    (21, 1, 4),   -- Student 21 in Project 4
    (22, 1, 4),   -- Student 22 in Project 4
    (23, 1, 4),   -- Student 23 in Project 4
    (24, 1, 4),   -- Student 24 in Project 4
    (25, 1, 4),   -- Student 25 in Project 4
    (26, 1, 5),   -- Student 26 in Project 5
    (27, 1, 5),   -- Student 27 in Project 5
    (28, 1, 5),   -- Student 28 in Project 5
    (29, 1, 5),   -- Student 29 in Project 5
    (30, 1, 5),   -- Student 30 in Project 5
    (4, 1, 6),   -- Student 4 in Project 6
    (32, 1, 6),   -- Student 32 in Project 6
    (33, 1, 6),   -- Student 33 in Project 6
    (34, 1, 6),   -- Student 34 in Project 6
    (35, 1, 6),   -- Student 35 in Project 6
    (31, 1, null);

INSERT INTO assignment (`subject_id`,`title`,`description`,`is_subject_assignment`)
VALUES
    (1,'Review Iteration 1','Review docs and code iteration 1 all group',1),
    (1,'Review Iteration 2','Review docs and code iteration 2 all group',1),
    (1,'Review Iteration 3','Review docs and code iteration 3 all group',1),
    (2,'Practice HTML','Do 10 exercise about HTML',1),
    (2,'Practice CSS','Do 13 exercise about CSS',1),
    (2,'Practice Javascript','Do 15 exercise about JS',1),
    (2,'Java Servlet','Intro to JavaServlet + JSP',1),
    (2,'Connect to Database','Learn JDBC',1),
    (3,'Requirement','Write requirement for SWP project',1),
    (3,'Write Document','Write SRS document for SWP project',1),
    (4,'Testcase design','Design testcase for login page, register page',1),
    (4,'Test technique','Dymanic test + static test',1),
    (6,'React App','Make React App to print "Hello React"',1),
    (6,'JS ES6','Learn new synctax in JS ES6',1);
