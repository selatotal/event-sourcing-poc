package br.com.grupoa.gaiaservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "professor")
public class ProfessorEntity {

    @Id
    private String codigo;
    private String nome;
    private String email;

    public ProfessorEntity() {
    }

    public ProfessorEntity(String codigo, String nome, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
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

}
