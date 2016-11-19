SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `jmail` DEFAULT CHARACTER SET utf8 ;
USE `jmail` ;

-- -----------------------------------------------------
-- Table `jmail`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `jmail`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `login` VARCHAR(255) NULL DEFAULT NULL UNIQUE ,
  `pass` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`user_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `jmail`.`letters`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `jmail`.`letters` (
  `letter_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(100) NULL DEFAULT NULL ,
  `body` VARCHAR(255) NULL DEFAULT NULL ,
  `to_user` INT(11) NOT NULL ,
  `from_user` INT(11) NOT NULL ,
  `send_date` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`letter_id`) ,
  INDEX `to_user` (`to_user` ASC) ,
  INDEX `from_user` (`from_user` ASC) ,
  CONSTRAINT `letters_ibfk_1`
    FOREIGN KEY (`to_user` )
    REFERENCES `jmail`.`users` (`user_id` ) ON UPDATE CASCADE ,
  CONSTRAINT `letters_ibfk_2`
    FOREIGN KEY (`from_user` )
    REFERENCES `jmail`.`users` (`user_id` ) ON UPDATE CASCADE )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `jmail` ;

INSERT INTO users (user_id, login, pass) VALUES (0, "DELETE_USER", "");
UPDATE users SET user_id = 0 WHERE user_id = 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;