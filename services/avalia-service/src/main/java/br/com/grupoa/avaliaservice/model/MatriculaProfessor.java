package br.com.grupoa.avaliaservice.model;


import java.util.Set;

public class MatriculaProfessor {

    private String idMatricula;
    private String codigoProfessor;
    private Set<TurmaDisciplina> turmasDisciplina;

    public MatriculaProfessor() {
    }

    public MatriculaProfessor(String idMatricula, String codigoProfessor, Set<TurmaDisciplina> turmasDisciplina) {
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

    public Set<TurmaDisciplina> getTurmasDisciplina() {
        return turmasDisciplina;
    }

    public void setTurmasDisciplina(Set<TurmaDisciplina> turmasDisciplina) {
        this.turmasDisciplina = turmasDisciplina;
    }
}
