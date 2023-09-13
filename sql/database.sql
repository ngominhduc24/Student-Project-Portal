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
  `status` BIT(1) NULL,
  `create_by` INT NULL,
  `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` INT NULL,
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
  `status` BIT(1) NULL,
  `full_name` VARCHAR(255) NULL,
  `gender` BIT(1) NULL,
  `date_of_birth` DATETIME NULL,
  `avatar_url` TEXT NULL,
  `role_id` INT NULL,
  `create_by` INT NULL,
  `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` INT NULL,
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
  `status` BIT(1) NULL,
  `description` VARCHAR(255) NULL,
  `create_by` INT NULL,
  `create_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` INT NULL,
  `update_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `subject_manager_id_idx` (`subject_manager_id` ASC) VISIBLE,
    FOREIGN KEY (`subject_manager_id`)
    REFERENCES `swp391`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
