drop schema exemplo cascade;
create schema exemplo;

GRANT USAGE ON SCHEMA exemplo TO exemplouser;

CREATE TABLE exemplo.evento
(
  id INTEGER NOT NULL, 
  titulo VARCHAR(100) NOT NULL,
  local VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  inicio DATE NOT NULL,
  termino DATE NOT NULL,
  inicio_inscricao DATE NOT NULL,
  termino_inscricao DATE NOT NULL,
  CONSTRAINT pk_evento PRIMARY KEY (id)
);
ALTER TABLE exemplo.evento
  OWNER TO exemplouser; 
CREATE SEQUENCE exemplo.seq_evento  START 1 INCREMENT 1;
ALTER SEQUENCE exemplo.seq_evento OWNER TO exemplouser;
  
CREATE TABLE exemplo.inscricao
(
  id INTEGER NOT NULL, 
  id_evento INTEGER NOT NULL,
  cpf VARCHAR(20) NOT NULL,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  tamanho_camiseta VARCHAR(5) NULL,
  CONSTRAINT pk_inscricao PRIMARY KEY (id),
  CONSTRAINT fk_inscricao_evento FOREIGN KEY (id_evento)
      REFERENCES exemplo.evento (id)
      ON UPDATE RESTRICT ON DELETE RESTRICT
);
ALTER TABLE exemplo.inscricao
  OWNER TO exemplouser;
CREATE SEQUENCE exemplo.seq_inscricao START 1 INCREMENT 1;
ALTER SEQUENCE exemplo.seq_inscricao OWNER TO exemplouser;  
