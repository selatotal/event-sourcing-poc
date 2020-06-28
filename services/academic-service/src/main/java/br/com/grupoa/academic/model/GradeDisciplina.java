package br.com.grupoa.academic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("gradeDisciplina")
public class GradeDisciplina implements AcademicEntity{

    @Id
    private String codigoGradeDisciplina;
    private String codigoGrade;
    private String codigoDisciplina;
    private String periodo;
    private String nomeGrade;
    private String nomeDisciplina;
    private Integer cargaHoraria;
    private String codigoCurso;
    private String nomeCurso;

    public GradeDisciplina() {
    }

    public GradeDisciplina(String codigoGradeDisciplina, String codigoGrade, String codigoDisciplina, String periodo, String nomeGrade, String nomeDisciplina, Integer cargaHoraria, String codigoCurso, String nomeCurso) {
        this.codigoGradeDisciplina = codigoGradeDisciplina;
        this.codigoGrade = codigoGrade;
        this.codigoDisciplina = codigoDisciplina;
        this.periodo = periodo;
        this.nomeGrade = nomeGrade;
        this.nomeDisciplina = nomeDisciplina;
        this.cargaHoraria = cargaHoraria;
        this.codigoCurso = codigoCurso;
        this.nomeCurso = nomeCurso;
    }

    public GradeDisciplina(String id) {
        this.codigoGradeDisciplina = id;
    }

    public String getCodigoGradeDisciplina() {
        return codigoGradeDisciplina;
    }

    public void setCodigoGradeDisciplina(String codigoGradeDisciplina) {
        this.codigoGradeDisciplina = codigoGradeDisciplina;
    }

    public String getCodigoGrade() {
        return codigoGrade;
    }

    public void setCodigoGrade(String codigoGrade) {
        this.codigoGrade = codigoGrade;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNomeGrade() {
        return nomeGrade;
    }

    public void setNomeGrade(String nomeGrade) {
        this.nomeGrade = nomeGrade;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    @Override
    public void defineEmptyId() {
        this.codigoGradeDisciplina = "";
    }
}
