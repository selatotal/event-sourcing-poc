package br.com.grupoa.academic.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "aluno")
public class Aluno implements AcademicEntity {

    @Id
    private String codigo;
    private String nome;
    private String email;
    private String ra;

    public Aluno() {
    }

    public Aluno(String codigo, String nome, String email, String ra) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.ra = ra;
    }

    public Aluno(String id) {
        this.codigo = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    @Override
    public void defineEmptyId() {
        this.codigo = "";
    }
}
