-- MySQL Workbench Forward Engineering

CREATE SCHEMA IF NOT EXISTS `RacingManagerG40`;
USE `RacingManagerG40`;

CREATE TABLE IF NOT EXISTS `Carro` (
  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Marca` VARCHAR(20) NOT NULL,
  `Modelo` VARCHAR(20) NOT NULL,
  `Categoria` VARCHAR(10) NOT NULL,
  `Cilindrada` INT UNSIGNED NOT NULL,
  `PotenciaICE` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE IF NOT EXISTS `Campeonato` (
  `Nome` VARCHAR(60) NOT NULL,
  `CorridasSimuladas` INT UNSIGNED NOT NULL,
  `VersaoCamp` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Circuito` (
  `Nome` VARCHAR(60) NOT NULL,
  `Comprimento` INT UNSIGNED NOT NULL,
  `Retas` INT UNSIGNED NOT NULL,
  `Curvas` INT UNSIGNED NOT NULL,
  `Chicanes` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Corrida` (
  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Voltas` INT UNSIGNED NOT NULL,
  `Clima_Seco` TINYINT(1) NOT NULL,
  `Nome_Circuito` VARCHAR(60) NOT NULL,
  `Nome_Campeonato` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  FOREIGN KEY (`Nome_Circuito`) REFERENCES `Circuito` (`Nome`),
  FOREIGN KEY (`Nome_Campeonato`) REFERENCES `Campeonato` (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Zona` (
  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Numero` INT UNSIGNED NOT NULL,
  `Tipo` VARCHAR(10) NOT NULL,
  `GDU` VARCHAR(15) NOT NULL,
  `Nome_Circuito` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`Id`),
  FOREIGN KEY (`Nome_Circuito`) REFERENCES `Circuito` (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Piloto` (
  `Nome` VARCHAR(45) NOT NULL,
  `CTS` FLOAT NOT NULL,
  `SVA` FLOAT NOT NULL,
  PRIMARY KEY (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Jogador` (
  `Nome` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `PontuacaoGlobal` INT UNSIGNED NOT NULL,
  `VersaoJogo` VARCHAR(10) NOT NULL,
  `Administrador` TINYINT(1) NOT NULL,
  PRIMARY KEY (`Nome`)
);

CREATE TABLE IF NOT EXISTS `Registo` (
  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nome_Jogador` VARCHAR(45) NOT NULL,
  `Nome_Campeonato` VARCHAR(45) NOT NULL,
  `Nome_Piloto` VARCHAR(45) NOT NULL,
  `Id_Carro` INT UNSIGNED NOT NULL,
  `PontuacaoCamp` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE (`Nome_Jogador`, `Nome_Campeonato`),
  FOREIGN KEY (`Nome_Jogador`) REFERENCES `Jogador` (`Nome`),
  FOREIGN KEY (`Nome_Campeonato`) REFERENCES `Campeonato` (`Nome`),
  FOREIGN KEY (`Nome_Piloto`) REFERENCES `Piloto` (`Nome`),
  FOREIGN KEY (`Id_Carro`) REFERENCES `Carro` (`Id`)
);

CREATE TABLE IF NOT EXISTS `Registo_Corrida` (
  `Id_Registo` INT UNSIGNED NOT NULL,
  `Id_Corrida` INT UNSIGNED NOT NULL,
  `PAC` FLOAT UNSIGNED NOT NULL,
  `ModoMotor` VARCHAR(15) NOT NULL,
  `Pneus` VARCHAR(10) NOT NULL,
  `Tempo` FLOAT NOT NULL,
  `Posicao` INT UNSIGNED NOT NULL,
  `PosCategoria` INT UNSIGNED NOT NULL,
  `Pontuacao` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Id_Registo`, `Id_Corrida`),
  FOREIGN KEY (`Id_Corrida`) REFERENCES `Corrida` (`Id`),
  FOREIGN KEY (`Id_Registo`) REFERENCES `Registo` (`Id`)
);

CREATE TABLE IF NOT EXISTS `Hibrido` (
  `Id_Carro` INT UNSIGNED NOT NULL,
  `PotenciaEletrica` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Id_Carro`),
  FOREIGN KEY (`Id_Carro`) REFERENCES `Carro` (`Id`)
);

