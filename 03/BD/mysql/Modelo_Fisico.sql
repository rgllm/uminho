-- MySQL Workbench Forward Engineering
drop  database if exists comboios;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema comboios
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comboios` DEFAULT CHARACTER SET utf8 ;
USE `comboios` ;

-- -----------------------------------------------------
-- Table `comboios`.`Comboio`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `comboios`.`Comboio` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `Carruagens` INT(11) NOT NULL,
  `Tipo` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
-- -----------------------------------------------------
-- Table `comboios`.`Utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comboios`.`Utilizador` (
  `Email` VARCHAR(50) NOT NULL,
  `Nome` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `comboios`.`Viagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comboios`.`Viagem` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `Origem` VARCHAR(45) NOT NULL,
  `Destino` VARCHAR(45) NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `Preço Base` DECIMAL(10,2) NOT NULL,
  `Data Partida` DATETIME NOT NULL,
  `Data Chegada` DATETIME NOT NULL,
  `Comboio_Id` INT(11) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Viagem_Comboio1_idx` (`Comboio_Id` ASC),
  CONSTRAINT `fk_Viagem_Comboio1`
    FOREIGN KEY (`Comboio_Id`)
    REFERENCES `comboios`.`Comboio` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `comboios`.`Reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comboios`.`Reserva` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Custo` DECIMAL(10,2) NOT NULL,
  `Classe` INT(11) NOT NULL,
  `Lugar` INT(11) NULL DEFAULT NULL,
  `Carruagem` INT(11) NULL DEFAULT NULL,
  `Nome` VARCHAR(50) NULL DEFAULT NULL,
  `CheckIn` DATETIME NULL DEFAULT NULL,
  `Utilizador_Email` VARCHAR(50) NOT NULL,
  `Viagem_id` INT(11) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Bilhete_Utilizador_idx` (`Utilizador_Email` ASC),
  INDEX `fk_Bilhete_Viagem1_idx` (`Viagem_id` ASC),
  CONSTRAINT `fk_Bilhete_Utilizador`
    FOREIGN KEY (`Utilizador_Email`)
    REFERENCES `comboios`.`Utilizador` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bilhete_Viagem1`
    FOREIGN KEY (`Viagem_id`)
    REFERENCES `comboios`.`Viagem` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

DELIMITER $$
CREATE TRIGGER `validaEmail` BEFORE INSERT ON `Utilizador`
FOR EACH ROW
BEGIN
IF New.Email NOT LIKE '%_@_%.__%' THEN
    SIGNAL SQLSTATE '10000'
    SET MESSAGE_TEXT = 'O Email não é válido';
END IF;
IF (length(New.`Password`)<6) THEN
    SIGNAL SQLSTATE '10000'
    SET MESSAGE_TEXT = 'A password tem de possuir pelo menos 6 caracteres';
END IF;
END$$   
DELIMITER ;


DELIMITER $$
CREATE TRIGGER `validaComboioBI` BEFORE INSERT ON `Comboio`
FOR EACH ROW
BEGIN
	IF New.Carruagens <=0 THEN
		SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'O comboio tem que ter pelo menos uma carruagem';
    END IF;
    IF New.Tipo!='Urbano' and New.Tipo!='Alfa' and New.Tipo!='Regional' and New.Tipo!='Intercidades' THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Um comboio só pode ser \'Urbano\', \'Alfa\' (Alfa pendular), \'Regional\' ou \'Intercidades\'';
    END IF;
    IF (New.Tipo='Alfa' or New.Tipo='Intercidades') and (New.Carruagens<2) THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'O comboio tem que ter pelo menos 2 carruagens (1ª e 2ª classe)';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `validaComboioBU` BEFORE UPDATE ON `Comboio`
FOR EACH ROW
BEGIN
	IF New.Carruagens <=0 THEN
		SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'O comboio tem que ter pelo menos uma carruagem';
    END IF;
    IF New.Tipo!='Urbano' and New.Tipo!='Alfa' and New.Tipo!='Regional' and New.Tipo!='Intercidades' THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Um comboio só pode ser \'Urbano\', \'Alfa\' (Alfa pendular), \'Regional\' ou \'Intercidades\'';
    END IF;
    IF (New.Tipo='Alfa' or New.Tipo='Intercidades') and (New.Carruagens<2) THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'O comboio tem que ter pelo menos 2 carruagens (1ª e 2ª classe)';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `validaViagemBI` BEFORE INSERT ON `Viagem`
FOR EACH ROW
BEGIN
    IF New.Tipo!='Nacional' and New.Tipo!='Internacional' THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Uma viajem só pode ser \'Nacional\' ou \'Internacional\'';
    END IF;


    IF (select count(*) from Viagem
    where Comboio_Id= New.Comboio_Id AND (New.`Data Partida` BETWEEN `Data Partida` AND`Data Chegada` OR
										New.`Data Chegada` BETWEEN `Data Partida` AND `Data Chegada` OR
                                        (New.`Data Partida` < `Data Partida` AND New.`Data Chegada` > `Data Chegada` ))) <> 0 THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Este comboio já faz uma viagem no período fornecido';
    END IF;

    IF New.`Data Chegada`<=New.`Data Partida` THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'A data de chegada tem que ser posterior à data de partida';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `validaViagemBU` BEFORE UPDATE ON `Viagem`
FOR EACH ROW
BEGIN
    IF New.Tipo!='Nacional' and New.Tipo!='Internacional' THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Uma viajem só pode ser \'Nacional\' ou \'Internacional\'';
    END IF;


    IF (select count(*) from Viagem
    where Comboio_Id= New.Comboio_Id AND (New.`Data Partida` BETWEEN `Data Partida` AND`Data Chegada` OR
										New.`Data Chegada` BETWEEN `Data Partida` AND `Data Chegada` OR
                                        (New.`Data Partida` < `Data Partida` AND New.`Data Chegada` > `Data Chegada` ))) <> 0 THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Este comboio já faz uma viagem no período fornecido';
    END IF;

    IF New.`Data Chegada`<=New.`Data Partida` THEN
        SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'A data de chegada tem que ser posterior à data de partida';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `validaReservaBI` BEFORE INSERT ON `Reserva`
FOR EACH ROW
BEGIN
    IF((select Tipo from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id))  = 'Urbano' OR
	(select Tipo from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id)) = 'Regional') THEN
        IF ( (select Carruagens from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id )) *10 = ocupados(New.Viagem_id)) THEN
            SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem';
		ELSE SET New.Lugar= 1+ (select count(*) from Reserva where Viagem_id=New.Viagem_id) % 10;
			 SET New.Carruagem = 1+ (select count(*) from Reserva where Viagem_id=New.Viagem_id) div 10;
        END IF;
        IF (New.Custo > (select `Preço Base` from Viagem where Id=New.Viagem_id)) THEN
			SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'O custo da reserva para uma viagem de classe normal tem que ser menor ou igual que o preço base dessa viagem';
        END IF;
        IF (New.Classe = 1) THEN
			SIGNAL SQLSTATE '10000'
			SET MESSAGE_TEXT = 'Não existe 1ª classe em comboios Urbanos/Regionais';
		ELSEIF ( New.Classe <>2) THEN
			SIGNAL SQLSTATE '10000'
			SET MESSAGE_TEXT = 'A classe só pode ser 1 ou 2';
		END IF;
        
	ELSE 
		IF (New.Classe = 1) THEN
			IF (select count(*) from Reserva where Viagem_id = New.Viagem_id and Classe=1)>=10 THEN
				SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem nesta classe';
			ELSE SET New.Carruagem = 1;
				 SET New.Lugar = 1+ (select count(*) from Reserva where Viagem_id=New.Viagem_id and Classe= 1);
			END IF;
			IF (New.Custo < (select `Preço Base` from Viagem where Id=New.Viagem_id)) THEN
				SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'O custo da reserva para uma viagem de 1ª classe tem que ser maior que o preço base dessa viagem';
			END IF;
		ELSEIF (New.Classe=2) THEN 
			IF (select count(*) from Reserva where Viagem_id = New.Viagem_id and Classe=2)>= 
			10 *((select Carruagens from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id))-1)  THEN
				SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem nesta classe';
			ELSE SET New.Lugar= 1+ (select count(*) from Reserva where Viagem_id=New.Viagem_id and Classe= 2) % 10;
				SET New.Carruagem = 2+ (select count(*) from Reserva where Viagem_id=New.Viagem_id and Classe= 2) div 10;
			END IF;
			IF (New.Custo > (select `Preço Base` from Viagem where Id=New.Viagem_id)) THEN
				SIGNAL SQLSTATE '10000'
				SET MESSAGE_TEXT = 'O custo da reserva para uma viagem de classe normal tem que ser menor ou igual que o preço base dessa viagem';
			END IF;
		ELSE SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'A classe só pode ser 1 ou 2';
		END IF;
	END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `validaReservaBU` BEFORE UPDATE ON `Reserva`
FOR EACH ROW
BEGIN
    IF((select Tipo from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id))  = 'Urbano' OR
        (select Tipo from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id)) = 'Regional') THEN
        IF ( (select Carruagens from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id )) *10 = ocupados(New.Viagem_id)) THEN
            SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem';
        END IF;
	ELSEIF (New.Classe = 1) THEN
		IF (select count(*) from Reserva where Viagem_id = New.Viagem_id and Classe=1)>=10 THEN
            SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem nesta classe';
		END IF;
		IF (New.Custo < (select `Preço Base` from Viagem where Id=New.Viagem_id)) THEN
			SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'O custo da reserva para uma viagem de 1ª classe tem que ser maior que o preço base dessa viagem';
        END IF;
    ELSE IF (select count(*) from Reserva where Viagem_id = New.Viagem_id and Classe=2)>=
			10 *(select Carruagens from Comboio where Id= (select Comboio_Id from Viagem where Id= New.Viagem_id))  THEN
			SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'Já não há lugares disponíveis para esta viagem nesta classe';
		END IF;
        IF (New.Custo > (select `Preço Base` from Viagem where Id=New.Viagem_id)) THEN
			SIGNAL SQLSTATE '10000'
            SET MESSAGE_TEXT = 'O custo da reserva para uma viagem de classe normal tem que ser menor ou igual que o preço base dessa viagem';
        END IF;
    END IF;
END$$
DELIMITER ;

 -- ocupados(idViajem INT) : dado um id de uma viajem retorna o número de lugares ocupados nessa viajem
DELIMITER $$
CREATE FUNCTION `ocupados` (idViagem INTEGER)
RETURNS INTEGER
BEGIN
RETURN( select count(*) from Reserva where Viagem_id = idViagem);
END$$
DELIMITER ;

DELIMITER $$
USE `comboios`$$
CREATE PROCEDURE `check_In` (in id_reserva int)
BEGIN
	UPDATE Reserva SET CheckIn= NOW() WHERE Id=id_reserva;
END$$

DELIMITER ;


-- -----------------------------------------------------
-- Povoamento
-- -----------------------------------------------------
INSERT INTO Comboio (Tipo,Carruagens)
VALUES
( 'Intercidades',4) ,
( 'Alfa',2) ,
( 'Alfa',5) ,
( 'Alfa',5) ,
( 'Alfa',4) ,
( 'Regional',2) ,
( 'Urbano',2) ,
( 'Urbano',5) ,
( 'Alfa',2) ,
( 'Urbano',2) ,
( 'Urbano',2) ,
( 'Alfa',5) ,
( 'Regional',4) ,
( 'Alfa',3) ,
( 'Intercidades',5) ,
( 'Intercidades',3) ,
( 'Intercidades',2) ,
( 'Urbano',2) ,
( 'Alfa',3) ,
( 'Intercidades',3) ,
( 'Urbano',2) ,
( 'Regional',2) ,
( 'Urbano',4) ,
( 'Urbano',2) ,
( 'Alfa',3) ,
( 'Regional',5) ,
( 'Alfa',4) ,
( 'Urbano',5) ,
( 'Alfa',3) ,
( 'Intercidades',3) ,
( 'Intercidades',5) ,
( 'Alfa',5) ,
( 'Alfa',2) ,
( 'Regional',5) ,
( 'Urbano',2) ,
( 'Regional',4) ,
( 'Urbano',5) ,
( 'Regional',3) ,
( 'Intercidades',5) ,
( 'Regional',5) ,
( 'Urbano',2) ,
( 'Urbano',3) ,
( 'Alfa',2) ,
( 'Regional',3) ,
( 'Intercidades',3) ,
( 'Regional',2) ,
( 'Urbano',2) ,
( 'Urbano',2) ,
( 'Urbano',3) ,
( 'Regional',4);

INSERT INTO Utilizador (Email,Nome,Password)
VALUES 
('0f486@hotmail.com','Mirela Alexandra','t7204qnr18'),
('fr7lvcx12@hotmail.com','Estér Marciano','3k6nvq6yudh2'),
('cr5ma0j@hotmail.com','Inês Weiyi','pxyfra5w'),
('96kekf2x@hotmail.com','Cristiana Isabel','vaurcrb7mvspc'),
('9pilxu@hotmail.com','Oryana Patrícia','kbfgyq3v'),
('aseg1zf@hotmail.com','Amélia Leonor','shrxdumir'),
('u9ds32y@hotmail.com','Ana Jade','yrfq2w6af24vo'),
('o2cs7d@hotmail.com','Constança da Graça','azrmup5t67y'),
('skqlr@hotmail.com','Sónia Naiara','ia84bgp5'),
('4ggp09i0nq@hotmail.com','Mónica Vanessa','wu92ukfw7k'),
('7ilb84@hotmail.com','Sheila Cloe','3cmiwf3'),
('yov47@hotmail.com','Margarida Francisca','xtnwyndp4y5m'),
('ipc9wzwsif@hotmail.com','Lua Oriana','vkmyh8so34lt'),
('kze1lf7v@hotmail.com','Joana de Sales','4dk0dj'),
('yaufb03r8@hotmail.com','Matilde Iracema','xelzxp2'),
('dece2a@hotmail.com','Leila Filipa','i843o53k'),
('lyaiqb3ft6@hotmail.com','Madalena Isabela','tmqarpey29cl'),
('0gynvpt@hotmail.com','Nayara Alexandra','usddkhln'),
('u79uue4o5r@hotmail.com','Mara Eduarda','pqzjac5y33tn'),
('3i9c0neu@hotmail.com','Renata Daniela','he7ltkzqeesr0'),
('261djo5ulq@hotmail.com','Núria Lorena','pj0ot8m3twzaf'),
('o5hei@hotmail.com','Jasmine Kyara','q9vm1v39'),
('bs65m5k3@hotmail.com','Ayla Gabriela','rt4qmkczyrv'),
('ltzaihuzgw@hotmail.com','Riyaa Hemal','52c2jodkng5jz'),
('m1kxce6e@hotmail.com','Matilde Elawoko','z9ahpryujh'),
('qlih9jmt@hotmail.com','Michelly Jahel','qgjqzmx'),
('dmp4l@hotmail.com','Catarina Salomé','psmuad7tehe6'),
('fbibj762xn@hotmail.com','Magda Ester','uegynjrdhsd'),
('s3zcpdo2@hotmail.com','Liliana Raissa','32uzeyrqz'),
('ebopo5ig@hotmail.com','Margarida Nair','auohybflcx82'),
('zg7dr1y70u@hotmail.com','Dahlia Sofia','cgssf1f7t2sn'),
('n40em9@hotmail.com','Clara Pedro','3znjwto67wx'),
('61268j@hotmail.com','Ciara Reiny','uipnwp2'),
('z5f7qg@hotmail.com','Denise Fátima','wjuiwh'),
('sjkh3r@hotmail.com','Miriam Gabriela','f2704t2ttvl01'),
('z90c22j@hotmail.com','Érica dos Anjos','vwo4z8l12kj81'),
('0q4l2fzhjs@hotmail.com','Kyara Fabiana','e2l5pywcx'),
('46h4w34@hotmail.com','Joyciane Alícia','9buj9lgx6zrd'),
('ilq5sko@hotmail.com','Jandira Sofia','a3e25or7l7oj'),
('ol3orehlp2@hotmail.com','Inês Juliana','kcwy244s3'),
('s27rkland@hotmail.com','Inês Félix','rky4avlwbmls1'),
('jxx0u@hotmail.com','Íris Dalene','upovuikce'),
('lvo9yh@hotmail.com','Nayara Vitória','c3pp9r88'),
('tztave@hotmail.com','Débora Raísa','3l6n09'),
('ijw8jlmbqm@hotmail.com','Sofia Aisha','hymprusbi80lr'),
('fh24m2@hotmail.com','Nathalia Maria','pdoz0wpvqv'),
('2c7neaum@hotmail.com','Vanessa Isabel','kkaggim'),
('jd8aa5@hotmail.com','Isabel Calisto','kaipnm998kf'),
('cu76tp@hotmail.com','Íris Jovana','ll55wh5zqxbl'),
('yc99k@hotmail.com','Nicole Solange','xruorr3knk');

INSERT INTO `comboios`.`Viagem` (Origem,Destino,Tipo,`Preço Base`,`Data Partida`,`Data Chegada`,Comboio_Id)
VALUES 	('Porto','Braga','Nacional',5.2,'2016-11-18 8:00:00','2016-11-18 8:30:00',7),
		( 'Lisboa','Madrid','Internacional',98.0,'2016-11-18 20:00:00','2016-11-19 7:50:00',2),
        ('Porto','Lisboa','Nacional',56.0,'2016-11-19 12:00:00','2016-11-19 13:30:00',3),
        ('Faro','Lisboa','Nacional',67.32,'2016-11-19 12:00:00','2016-11-19 13:30:00',4),
        ('Braga','Lisboa','Nacional',40,'2017-01-02 06:07:00','2017-01-02 10:21:00',14),
        ('Braga','Porto','Nacional',10,'2016-12-22 14:34:00','2016-12-22 15:21:00',31),
        ('Porto','Vigo','Internacional',58.34,'2016-11-24 22:07:00','2016-11-25 01:21:00',43),
        ('Braga','Viana do Castelo','Nacional',12.27,'2016-12-22 11:34:00','2016-12-22 13:21:00',13),
        ('Lisboa','Cascais','Nacional',4.56,'2016-12-02 11:04:00','2016-12-02 11:15:00',7);
        

INSERT INTO `comboios`.`Reserva` (`Custo`, `Classe`, `Nome`, `Utilizador_Email`, `Viagem_id`) 
	VALUES ('10.45', '2', 'Amélia Leonor', 'aseg1zf@hotmail.com', '8'),
		   ('245.32', '1', 'Madalena Isabela', 'lyaiqb3ft6@hotmail.com', '2'),
		   ('34.57', '2', 'Érica dos Anjos', 'z90c22j@hotmail.com', '5'),
           ('58.34', '2', 'Constança da Graça', 'o2cs7d@hotmail.com', '7'),
		   ('56.38', '2', 'Inês Félix', 's27rkland@hotmail.com', '4'),
           ('89.87', '1', 'Jasmine Kyara', 'o5hei@hotmail.com', '3');

-- -----------------------------------------------------
-- UTILIZADORES`
-- -----------------------------------------------------

DROP USER 'admin'@'localhost';

-- Criação do utilizador 'administrador'
CREATE USER 'admin'@'localhost';
SET PASSWORD FOR 'admin'@'localhost' = PASSWORD('admin123');
GRANT ALL ON `comboios`.* TO 'admin'@'localhost';


DROP USER 'bilheteira'@'localhost';

-- Criação do utilizador 'bilheteira'
CREATE USER 'bilheteira'@'localhost';
SET PASSWORD FOR 'bilheteira'@'localhost' = PASSWORD('bilhetes');
GRANT SELECT, INSERT, UPDATE ON  `comboios`.* TO 'bilheteira'@'localhost';


-- -----------------------------------------------------
-- VIEWS
-- -----------------------------------------------------

CREATE VIEW Utilizadores AS
	SELECT DC.Nome AS Nome, DC.Email AS Email
		FROM Utilizador AS DC;

CREATE VIEW NrLugaresReservados AS
    SELECT Viagem_id AS 'ID da Viagem', COUNT(*) AS 'Lugares Reservados' FROM Reserva WHERE Viagem_Id IN(
		SELECT DISTINCT Viagem_Id FROM Reserva)
        GROUP BY Viagem_Id;

-- -----------------------------------------------------
-- TRANSACTION
-- -----------------------------------------------------

DELIMITER $$
CREATE PROCEDURE inserirReserva
	(IN Custo DECIMAL(10,2), Classe INT, Nome VARCHAR(50), Email VARCHAR(50), Viagem INT)
BEGIN
	-- Declaraçãoo de um handler para tratamento de erros.
    DECLARE ErroTransacao BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET ErroTransacao = 1;
	-- Início da transação
	START TRANSACTION;
	-- 1ª Operação - INSERT
	INSERT INTO Reserva
		(comboios.Custo,comboios.Classe,comboios.Nome,comboios.Utilizador_Email,comboios.Viagem_ID)
		VALUES(Custo,Classe,Nome,Email,Viagem);
	-- Verificação da ocorrência de um erro.
    IF ErroTransacao THEN
		-- Desfazer as operações realizadas.
        ROLLBACK;
    ELSE
		-- Confirmar as operações realizadas.
        COMMIT;
    END IF;
END $$