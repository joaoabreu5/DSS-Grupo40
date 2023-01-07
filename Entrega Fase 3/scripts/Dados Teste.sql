USE RacingManagerG40;

INSERT INTO Campeonato
(Nome, CorridasSimuladas, VersaoCamp)
VALUES
('F1 2023', 0, 'Premium'),
('WEC 2023', 0, 'Base');

INSERT INTO Circuito
(Nome, Comprimento, Retas, Curvas, Chicanes)
VALUES
('Falperra', 5200, 6, 5, 1),
('Monaco', 3337, 6, 5, 1);

INSERT INTO Zona
(Numero, Tipo, GDU, Nome_Circuito)
VALUES
(1, 'Reta', 'Possível', 'Falperra'),
(1, 'Curva', 'Possível', 'Falperra'),
(2, 'Reta', 'Difícil', 'Falperra'),
(2, 'Curva', 'Impossível', 'Falperra'),
(3, 'Reta', 'Possível', 'Falperra'),
(1, 'Chicane', 'Difícil', 'Falperra'),
(4, 'Reta', 'Possível', 'Falperra'),
(3, 'Curva', 'Difícil', 'Falperra'),
(5, 'Reta', 'Possível', 'Falperra'),
(4, 'Curva', 'Impossível', 'Falperra'),
(6, 'Reta', 'Possível', 'Falperra'),
(5, 'Curva', 'Possível', 'Falperra'),
(1, 'Reta', 'Possível', 'Monaco'),
(1, 'Curva', 'Possível', 'Monaco'),
(2, 'Reta', 'Difícil', 'Monaco'),
(2, 'Curva', 'Impossível', 'Monaco'),
(3, 'Reta', 'Possível', 'Monaco'),
(1, 'Chicane', 'Difícil', 'Monaco'),
(4, 'Reta', 'Possível', 'Monaco'),
(3, 'Curva', 'Difícil', 'Monaco'),
(5, 'Reta', 'Possível', 'Monaco'),
(4, 'Curva', 'Impossível', 'Monaco'),
(6, 'Reta', 'Possível', 'Monaco'),
(5, 'Curva', 'Possível', 'Monaco');

INSERT INTO Corrida
(Voltas, Clima_Seco, Nome_Circuito, Nome_Campeonato)
VALUES
(40, TRUE, 'Falperra', 'F1 2023'),
(78, FALSE, 'Monaco', 'F1 2023'),
(50, FALSE, 'Falperra', 'WEC 2023'),
(80, TRUE, 'Monaco', 'WEC 2023');

INSERT INTO Carro
(Marca, Modelo, Categoria, Cilindrada, PotenciaICE)
VALUES
('Mercedes', 'W11', 'C1H', 1600, 750),
('Ferrari', 'F2004', 'C1', 3000, 1000),
('Porsche', '919 Hybrid', 'C2H', 2000, 500),
('Rebellion', 'R13', 'C2', 4500, 700),
('Porsche', '911 GT3 R Hybrid', 'GTH', 4000, 470),
('Porsche', '911 RSR', 'GT', 4200, 500),
('Ford', 'Mustang', 'SC', 3500, 350);

INSERT INTO Hibrido
(Id_Carro, PotenciaEletrica)
VALUES
(1, 250),
(3, 400),
(5, 200);

INSERT INTO Piloto
(Nome, CTS, SVA)
VALUES
('Ayrton Senna', 1, 0.9),
('Michael Schumacher', 0.9, 0.9),
('Alain Prost', 0.4, 0.5),
('Fernando Alonso', 0.65, 0.7);

INSERT INTO Jogador
(Nome, Password, PontuacaoGlobal, VersaoJogo, Administrador)
VALUES
('Ricardo', 'Ricardo', 0, 'Premium', TRUE),
('Rui', 'Rui', 0, 'Base', FALSE);
