package br.com.grupoa.academic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@RedisHash("matriculaProfessor")
public class MatriculaProfessor implements AcademicEntity{

    @Id
    private String idMatricula;
    private String codigoProfessor;
    private Set<String> turmasDisciplina;

    public MatriculaProfessor() {
    }

    public MatriculaProfessor(String idMatricula, String codigoProfessor, Set<String> turmasDisciplina) {
        this.idMatricula = idMatricula;
        this.codigoProfessor = codigoProfessor;
        this.turmasDisciplina = turmasDisciplina;
    }

    public MatriculaProfessor(String id) {
        this.idMatricula = id;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getCodigoProfessor() {
        return codigoProfessor;
    }

    public void setCodigoProfessor(String codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
    }

    public Set<String> getTurmasDisciplina() {
        return turmasDisciplina;
    }

    public void setTurmasDisciplina(Set<String> turmasDisciplina) {
        this.turmasDisciplina = turmasDisciplina;
    }

    @Override
    public void defineEmptyId() {
        this.idMatricula = "";
    }

}
