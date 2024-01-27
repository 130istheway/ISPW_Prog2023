-- Active: 1706304468707@@127.0.0.1@3306@lasciafareanoi
DROP DATABASE IF EXISTS stefyispw;


CREATE DATABASE stefyispw
    DEFAULT CHARACTER SET = 'utf8mb4';


USE stefyispw;

-- -----------------------------------------------------
-- Table `LOGIN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LOGIN` ;

CREATE TABLE IF NOT EXISTS `LOGIN` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `ROLE` VARCHAR(45) NOT NULL DEFAULT 'UTENTE',
  PRIMARY KEY (`ID`));


-- -----------------------------------------------------
-- Table `ARTICOLI`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ARTICOLI` ;

CREATE TABLE IF NOT EXISTS `ARTICOLI` (
  `idARTICOLI` INT NOT NULL AUTO_INCREMENT,
  `ARTICOblob` BLOB NOT NULL,
  `idNegozio` INT NOT NULL,
  PRIMARY KEY (`idARTICOLI`),
  INDEX `Negozio` (`idNegozio` ASC));


-- -----------------------------------------------------
-- Table `LISTE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LISTE` ;

CREATE TABLE IF NOT EXISTS `LISTE` (
  `IDListe` INT NOT NULL,
  `LISTEblob` BLOB NOT NULL,
  PRIMARY KEY (`IDListe`),
  CONSTRAINT `fk_LISTE_LOGIN`
    FOREIGN KEY (`IDListe`)
    REFERENCES `LOGIN` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `ArticoNegozi`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ArticoNegozi` ;

CREATE TABLE IF NOT EXISTS `ArticoliNegozi` (
  `idNegozio` INT NOT NULL,
  `idArticolo` INT NOT NULL,
  PRIMARY KEY (`idNegozio`, `idArticolo`),
  INDEX `idArticolo_idx` (`idArticolo` ASC),
  CONSTRAINT `idNegozio`
    FOREIGN KEY (`idNegozio`)
    REFERENCES `LOGIN` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idArticolo`
    FOREIGN KEY (`idArticolo`)
    REFERENCES `ARTICOLI` (`idARTICOLI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



SET SQL_MODE = '';
DROP USER IF EXISTS amministratore;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'amministratore' IDENTIFIED BY 'amministratore';

GRANT INSERT, DELETE, SELECT ON TABLE `LOGIN` TO 'amministratore';
GRANT INSERT, SELECT, DELETE ON TABLE `ARTICOLI` TO 'amministratore';
GRANT SELECT, INSERT, DELETE ON TABLE `LISTE` TO 'amministratore';
SET SQL_MODE = '';
DROP USER IF EXISTS login;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'login' IDENTIFIED BY 'login';

GRANT SELECT ON TABLE `myStefy`.`LOGIN` TO 'login';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO login (USERNAME, PASSWORD, ROLE) VALUES ('administrator', 'administrator', 'AMMINISTRATOR');
INSERT INTO login (USERNAME, PASSWORD, ROLE) VALUES ('gigi', 'gigi', 'NEGOZIO');
INSERT INTO login (USERNAME, PASSWORD, ROLE) VALUES ('lollo', 'lollo', 'UTENTE');
INSERT INTO login (USERNAME, PASSWORD, ROLE) VALUES ('jonathanjoestar', 'hermitpurple', 'AMMINISTRATOR');





###### QUERY di prova ##########################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################
INSERT INTO articoli (ARTICOblob, idNegozio) VALUES ('{pane}{0|nome|54.0|43.0}{[hole, popo]|4.0}{3|4|true|utesigehrtfbavgyuaeryvehghuerihguefrhedsfhvuygerghieurghergh}', 4);

INSERT INTO articolinegozi (idNegozio, idArticolo) VALUES (3, 5);

DELETE FROM articoli WHERE idARTICOLI = 3;

SELECT COUNT(idARTICOLI)as number FROM articoli WHERE `idNegozio` = "3";

SELECT * FROM articoli

SELECT * FROM articolinegozi;

SELECT idArticolo as ARTICOLO FROM articolinegozi WHERE idNegozio = 4;


SELECT `ARTICOblob` as DATI FROM articoli WHERE `idARTICOLI` = 300000457 LIMIT 1 ;

SELECT * FROM login

SELECT ID FROM login WHERE username = "gigi" AND password = "gigi" LIMIT 1;