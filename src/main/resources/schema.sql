CREATE TABLE autor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT,
    instante_criacao TIMESTAMP
);

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

create table livro (
        id bigint not null,
        autor_id integer not null,
        categoria_id integer not null,
        data_publicacao date not null,
        isbn varchar(255) not null,
        numero_paginas integer not null check (numero_paginas>=100),
        preco numeric(38,2) not null check (preco>=20),
        resumo varchar(500) not null,
        sumario varchar(4000),
        titulo varchar(255) not null,
        primary key (id)
);