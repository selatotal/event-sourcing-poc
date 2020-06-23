package br.com.selat.professorservice.contract.v1.input;

public class ProfessorInput {

    private String name;
    private String email;

    public ProfessorInput() {
    }

    public ProfessorInput(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
