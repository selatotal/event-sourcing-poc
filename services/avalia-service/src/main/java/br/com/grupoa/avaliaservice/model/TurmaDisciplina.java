package br.com.grupoa.avaliaservice.model;

import java.util.Set;

public class TurmaDisciplina {
    private String codigo;
    private String codigoTurma;
    private String nomeTurma;
    private String codigoGradeDisciplina;
    private Long inicio;
    private Long fim;
    private Set<String> professores;

    public TurmaDisciplina() {
    }

    public TurmaDisciplina(String codigo, String codigoTurma, String nomeTurma, String codigoGradeDisciplina, Long inicio, Long fim, Set<String> professores) {
        this.codigo = codigo;
        this.codigoTurma = codigoTurma;
        this.nomeTurma = nomeTurma;
        this.codigoGradeDisciplina = codigoGradeDisciplina;
        this.inicio = inicio;
        this.fim = fim;
        this.professores = professores;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getCodigoGradeDisciplina() {
        return codigoGradeDisciplina;
    }

    public void setCodigoGradeDisciplina(String codigoGradeDisciplina) {
        this.codigoGradeDisciplina = codigoGradeDisciplina;
    }

    public Long getInicio() {
        return inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Long getFim() {
        return fim;
    }

    public void setFim(Long fim) {
        this.fim = fim;
    }

    public Set<String> getProfessores() {
        return professores;
    }

    public void setProfessores(Set<String> professores) {
        this.professores = professores;
    }
}
