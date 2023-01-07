package Datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOconfig {
    static final String USERNAME = "DSSGrupo40";
    static final String PASSWORD = "DSSGrupo40";
    public static final String DATABASE = "RacingManagerG40";
    private static final String DRIVER = "jdbc:mysql";
    static final String URL = DRIVER + "://localhost:3306/";
    static final String URL_DB = URL + DATABASE;

    static void DAO_CreateTables()
    {
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.prepareStatement(" ");

            String sql = "CREATE DATABASE IF NOT EXISTS `" + DAOconfig.DATABASE + "`;";
            stm.executeUpdate(sql);

            sql = "USE `" + DAOconfig.DATABASE + "`;";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Carro` (\n" +
                    "  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `Marca` VARCHAR(20) NOT NULL,\n" +
                    "  `Modelo` VARCHAR(20) NOT NULL,\n" +
                    "  `Categoria` VARCHAR(10) NOT NULL,\n" +
                    "  `Cilindrada` INT UNSIGNED NOT NULL,\n" +
                    "  `PotenciaICE` INT UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Campeonato` (\n" +
                    "  `Nome` VARCHAR(60) NOT NULL,\n" +
                    "  `CorridasSimuladas` INT UNSIGNED NOT NULL,\n" +
                    "  `VersaoCamp` VARCHAR(10) NOT NULL,\n" +
                    "  PRIMARY KEY (`Nome`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Circuito` (\n" +
                    "  `Nome` VARCHAR(60) NOT NULL,\n" +
                    "  `Comprimento` INT UNSIGNED NOT NULL,\n" +
                    "  `Retas` INT UNSIGNED NOT NULL,\n" +
                    "  `Curvas` INT UNSIGNED NOT NULL,\n" +
                    "  `Chicanes` INT UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`Nome`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Corrida` (\n" +
                    "  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `Voltas` INT UNSIGNED NOT NULL,\n" +
                    "  `Clima_Seco` TINYINT(1) NOT NULL,\n" +
                    "  `Nome_Circuito` VARCHAR(60) NOT NULL,\n" +
                    "  `Nome_Campeonato` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`Id`),\n" +
                    "  FOREIGN KEY (`Nome_Circuito`) REFERENCES `Circuito` (`Nome`),\n" +
                    "  FOREIGN KEY (`Nome_Campeonato`) REFERENCES `Campeonato` (`Nome`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Zona` (\n" +
                    "  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `Numero` INT UNSIGNED NOT NULL,\n" +
                    "  `Tipo` VARCHAR(10) NOT NULL,\n" +
                    "  `GDU` VARCHAR(15) NOT NULL,\n" +
                    "  `Nome_Circuito` VARCHAR(60) NOT NULL,\n" +
                    "  PRIMARY KEY (`Id`),\n" +
                    "  FOREIGN KEY (`Nome_Circuito`) REFERENCES `Circuito` (`Nome`)\n" +
                    ");\n";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Piloto` (\n" +
                    "  `Nome` VARCHAR(45) NOT NULL,\n" +
                    "  `CTS` FLOAT NOT NULL,\n" +
                    "  `SVA` FLOAT NOT NULL,\n" +
                    "  PRIMARY KEY (`Nome`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Jogador` (\n" +
                    "  `Nome` VARCHAR(45) NOT NULL,\n" +
                    "  `Password` VARCHAR(45) NOT NULL,\n" +
                    "  `PontuacaoGlobal` INT UNSIGNED NOT NULL,\n" +
                    "  `VersaoJogo` VARCHAR(10) NOT NULL,\n" +
                    "  `Administrador` TINYINT(1) NOT NULL,\n" +
                    "  PRIMARY KEY (`Nome`)\n" +
                    ");\n";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Registo` (\n" +
                    "  `Id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `Nome_Jogador` VARCHAR(45) NOT NULL,\n" +
                    "  `Nome_Campeonato` VARCHAR(45) NOT NULL,\n" +
                    "  `Nome_Piloto` VARCHAR(45) NOT NULL,\n" +
                    "  `Id_Carro` INT UNSIGNED NOT NULL,\n" +
                    "  `PontuacaoCamp` INT UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`Id`),\n" +
                    "  UNIQUE (`Nome_Jogador`, `Nome_Campeonato`),\n" +
                    "  FOREIGN KEY (`Nome_Jogador`) REFERENCES `Jogador` (`Nome`),\n" +
                    "  FOREIGN KEY (`Nome_Campeonato`) REFERENCES `Campeonato` (`Nome`),\n" +
                    "  FOREIGN KEY (`Nome_Piloto`) REFERENCES `Piloto` (`Nome`),\n" +
                    "  FOREIGN KEY (`Id_Carro`) REFERENCES `Carro` (`Id`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Registo_Corrida` (\n" +
                    "  `Id_Registo` INT UNSIGNED NOT NULL,\n" +
                    "  `Id_Corrida` INT UNSIGNED NOT NULL,\n" +
                    "  `PAC` FLOAT UNSIGNED NOT NULL,\n" +
                    "  `ModoMotor` VARCHAR(15) NOT NULL,\n" +
                    "  `Pneus` VARCHAR(10) NOT NULL,\n" +
                    "  `Tempo` FLOAT NOT NULL,\n" +
                    "  `Posicao` INT UNSIGNED NOT NULL,\n" +
                    "  `PosCategoria` INT UNSIGNED NOT NULL,\n" +
                    "  `Pontuacao` INT UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`Id_Registo`, `Id_Corrida`),\n" +
                    "  FOREIGN KEY (`Id_Corrida`) REFERENCES `Corrida` (`Id`),\n" +
                    "  FOREIGN KEY (`Id_Registo`) REFERENCES `Registo` (`Id`)\n" +
                    ");";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Hibrido` (\n" +
                    "  `Id_Carro` INT UNSIGNED NOT NULL,\n" +
                    "  `PotenciaEletrica` INT UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`Id_Carro`),\n" +
                    "  FOREIGN KEY (`Id_Carro`) REFERENCES `Carro` (`Id`)\n" +
                    ");\n";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
}
