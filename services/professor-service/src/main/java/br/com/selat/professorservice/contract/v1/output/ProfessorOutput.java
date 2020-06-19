package br.com.selat.professorservice.contract.v1.output;

public class ProfessorOutput {

    private String id;
    private String name;

    public ProfessorOutput() {
    }

    public ProfessorOutput(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
