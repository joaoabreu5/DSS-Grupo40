-- CREATES USER 'DSSGrupo40' with password 'DSSGrupo40'

CREATE USER 'DSSGrupo40'@'localhost' IDENTIFIED BY 'DSSGrupo40';
GRANT ALL PRIVILEGES ON * . * TO 'DSSGrupo40'@'localhost';
FLUSH PRIVILEGES;