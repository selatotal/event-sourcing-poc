package br.com.selat.studentservice.contract.v1.input;

public class StudentInput {

    private String name;

    public StudentInput() {
    }

    public StudentInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
