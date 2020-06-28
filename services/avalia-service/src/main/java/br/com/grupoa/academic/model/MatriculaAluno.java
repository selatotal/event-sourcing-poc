package br.com.grupoa.academic.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class MatriculaAluno {

    @Id
    private String idMatricula;
    private String codigoAluno;
    private String periodoLetivo;
    private String curso;
    private String grade;
    private String periodo;
    private Set<String> turmasDisciplina;

    public MatriculaAluno() {
    }

    public MatriculaAluno(String idMatricula, String codigoAluno, String periodoLetivo, String curso, String grade, String periodo, Set<String> turmasDisciplina) {
        this.idMatricula = idMatricula;
        this.codigoAluno = codigoAluno;
        this.periodoLetivo = periodoLetivo;
        this.curso = curso;
        this.grade = grade;
        this.periodo = periodo;
        this.turmasDisciplina = turmasDisciplina;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(String codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public String getPeriodoLetivo() {
        return periodoLetivo;
    }

    public void setPeriodoLetivo(String periodoLetivo) {
        this.periodoLetivo = periodoLetivo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Set<String> getTurmasDisciplina() {
        return turmasDisciplina;
    }

    public void setTurmasDisciplina(Set<String> turmasDisciplina) {
        this.turmasDisciplina = turmasDisciplina;
    }
}
