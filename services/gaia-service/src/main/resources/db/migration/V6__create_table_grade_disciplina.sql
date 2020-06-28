create table grade_disciplina (
    codigo_grade_disciplina varchar(255) primary key not null,
    codigo_grade varchar(255),
    codigo_disciplina varchar(255),
    periodo varchar(255),
    nome_grade varchar(255),
    nome_disciplina varchar(255),
    carga_horaria integer,
    codigo_curso varchar(255),
    nome_curso varchar(255)
);