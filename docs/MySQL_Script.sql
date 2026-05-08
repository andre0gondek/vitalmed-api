create database distribuidora_db;
use distribuidora_db;

create table Usuario(
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(40),
    email VARCHAR(100),
    senha varchar(20),
    cargo varchar(50)
);

create table Categoria(
	id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome_categoria varchar(100)
);

create table Insumo(
	id_insumo INT AUTO_INCREMENT PRIMARY KEY,
    nome_insumo varchar(40),
    id_categoria INT NOT NULL,
    capacidade int,
    tamanho char,
    material varchar(40),
    estoque_minimo int default 0,
    estoque_atual int default 0,
    
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

create table Movimentacao(
	id_movimentacao int auto_increment primary key,
    id_insumo int not null,
    id_usuario int not null,
    tipo_movimentacao ENUM('ENTRADA', 'SAIDA') NOT NULL,
    qtd int,
    data_hora datetime,
    finalidade varchar(255),
    
    FOREIGN KEY (id_insumo) REFERENCES Insumo(id_insumo),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

INSERT INTO Usuario (nome_usuario, email, senha, cargo)
VALUES(
    "Carlos",
    "carlos@gmail.com",
    "senha123",
    "ALMOXARIFE"
);


ALTER TABLE Insumo MODIFY COLUMN tamanho varchar(15);
ALTER TABLE Insumo MODIFY COLUMN capacidade varchar(15);

INSERT INTO Categoria (nome_categoria)
VALUES ("Seringas"), ("Luvas"), ("Gaze");


INSERT INTO Insumo (nome_insumo, id_categoria, capacidade, estoque_minimo, estoque_atual)
VALUES (
    "Seringa Descartável C/ Agulha",
    1,
    '5ml',
    100,
    150
);

INSERT INTO Insumo (nome_insumo, id_categoria, tamanho, material, estoque_minimo, estoque_atual)
VALUES (
    'Luva de Procedimento em Látex',
    2,
    'M',
    'Látex',
    500,
    200
);

INSERT INTO Movimentacao (id_insumo, id_usuario, tipo_movimentacao, qtd, data_hora, finalidade)
VALUES (
    1,
    1,
    'SAIDA',
    300,
    '2026-04-28 15:30:00',
    'Recebimento de 300 und de Seringas descartáveis com agulha'
);

SELECT 
    m.id_movimentacao,
    m.data_hora,
    u.nome_usuario AS responsavel_operacao,
    m.tipo_movimentacao,
    m.qtd AS quantidade_movimentada,
    i.nome_insumo,
    c.nome_categoria,
    m.finalidade
FROM Movimentacao m
JOIN Insumo i ON m.id_insumo = i.id_insumo
JOIN Categoria c ON i.id_categoria = c.id_categoria
JOIN Usuario u ON m.id_usuario = u.id_usuario
ORDER BY m.data_hora DESC;