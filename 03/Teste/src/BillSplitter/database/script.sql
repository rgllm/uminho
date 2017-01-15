-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema billsplitter
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema billsplitter
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `billsplitter` DEFAULT CHARACTER SET utf8 ;
USE `billsplitter` ;

-- -----------------------------------------------------
-- Table `billsplitter`.`casa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`casa` (
  `idcasa` INT NOT NULL AUTO_INCREMENT,
  `morada` VARCHAR(60) NULL DEFAULT NULL,
  `cod_postal` VARCHAR(10) NULL DEFAULT NULL,
  `localidade` VARCHAR(45) NULL DEFAULT NULL,
  `telefone` VARCHAR(15) NULL DEFAULT NULL,
  `observacoes` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`idcasa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billsplitter`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`user` (
  `email` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `data_chegada` DATE NOT NULL,
  `data_saida` DATE NULL DEFAULT NULL,
  `password` VARCHAR(45) NOT NULL,
  `saldo` DECIMAL(10,2) NOT NULL,
  `casa_idcasa` INT NOT NULL,
  PRIMARY KEY (`email`),
  INDEX `fk_user_casa_idx` (`casa_idcasa` ASC),
  CONSTRAINT `fk_user_casa`
    FOREIGN KEY (`casa_idcasa`)
    REFERENCES `billsplitter`.`casa` (`idcasa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billsplitter`.`despesa_normal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`despesa_normal` (
  `iddespesa` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(15) NULL DEFAULT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `data_limite` DATE NULL DEFAULT NULL,
  `montante` DECIMAL(10,2) NOT NULL,
  `observacoes` VARCHAR(100) NULL DEFAULT NULL,
  `pagador` VARCHAR(45) NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL,
  `casa_idcasa` INT NOT NULL,
  PRIMARY KEY (`iddespesa`),
  INDEX `fk_despesa_normal_casa1_idx` (`casa_idcasa` ASC),
  CONSTRAINT `fk_despesa_normal_casa1`
    FOREIGN KEY (`casa_idcasa`)
    REFERENCES `billsplitter`.`casa` (`idcasa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billsplitter`.`despesa_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`despesa_has_user` (
  `despesa_user_id` INT NOT NULL,
  `user_email` VARCHAR(45) NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`despesa_user_id`, `user_email`),
  INDEX `fk_despesa_has_user_user1_idx` (`user_email` ASC),
  INDEX `fk_despesa_has_user_despesa1_idx` (`despesa_user_id` ASC),
  CONSTRAINT `fk_despesa_has_user_despesa1`
    FOREIGN KEY (`despesa_user_id`)
    REFERENCES `billsplitter`.`despesa_normal` (`iddespesa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_despesa_has_user_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `billsplitter`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billsplitter`.`despesa_reco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`despesa_reco` (
  `iddespesa_reco` INT NOT NULL,
  `descricao` VARCHAR(15) NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `data_limite` DATE NULL DEFAULT NULL,
  `montante` DECIMAL(10,2) NOT NULL,
  `periodo` VARCHAR(4) NOT NULL,
  `observacoes` VARCHAR(45) NULL,
  `casa_idcasa` INT NOT NULL,
  PRIMARY KEY (`iddespesa_reco`),
  INDEX `fk_despesa_reco_casa1_idx` (`casa_idcasa` ASC),
  CONSTRAINT `fk_despesa_reco_casa1`
    FOREIGN KEY (`casa_idcasa`)
    REFERENCES `billsplitter`.`casa` (`idcasa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `billsplitter`.`situacao_irregular`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billsplitter`.`situacao_irregular` (
  `email` VARCHAR(45) NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  `user_email` VARCHAR(45) NOT NULL,
  INDEX `fk_situacao_irregular_user1_idx` (`user_email` ASC),
  CONSTRAINT `fk_situacao_irregular_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `billsplitter`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
