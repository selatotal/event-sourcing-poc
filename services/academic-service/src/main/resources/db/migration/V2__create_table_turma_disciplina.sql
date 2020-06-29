create table turma_disciplina (
    codigo varchar(255) primary key not null,
    codigo_turma varchar(255),
    nome_turma varchar(255),
    codigo_grade_disciplina varchar(255),
    inicio bigint,
    fim bigint
);