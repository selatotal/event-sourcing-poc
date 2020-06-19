package br.com.selat.classroomservice.contract.v1.output;

public class ClassroomOutput {

    private String id;
    private String name;

    public ClassroomOutput() {
    }

    public ClassroomOutput(String id, String name) {
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
