package br.com.selat.academic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@RedisHash("turmaDisciplina")
public class TurmaDisciplina {
    @Id
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
