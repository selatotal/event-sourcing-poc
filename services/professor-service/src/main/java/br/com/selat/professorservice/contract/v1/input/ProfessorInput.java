package br.com.selat.professorservice.contract.v1.input;

public class ProfessorInput {

    private String name;

    public ProfessorInput() {
    }

    public ProfessorInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
