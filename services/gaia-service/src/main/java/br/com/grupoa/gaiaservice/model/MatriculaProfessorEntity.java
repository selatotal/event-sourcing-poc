package br.com.grupoa.gaiaservice.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Set;

@Entity(name = "matricula_professor")
public class MatriculaProfessorEntity {

    @Id
    private String idMatricula;
    private String codigoProfessor;
    @Transient
    private Set<String> turmasDisciplina;

    public MatriculaProfessorEntity() {
    }

    public MatriculaProfessorEntity(String idMatricula, String codigoProfessor, Set<String> turmasDisciplina) {
        this.idMatricula = idMatricula;
        this.codigoProfessor = codigoProfessor;
        this.turmasDisciplina = turmasDisciplina;
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
}
