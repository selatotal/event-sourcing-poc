package br.com.selat.classroomservice.contract.v1.input;

public class ClassroomInput {

    private String name;

    public ClassroomInput() {
    }

    public ClassroomInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
