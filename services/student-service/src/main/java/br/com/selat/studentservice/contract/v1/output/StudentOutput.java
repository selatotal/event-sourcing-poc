package br.com.selat.studentservice.contract.v1.output;

public class StudentOutput {

    private String id;
    private String name;
    private String email;
    private String ra;

    public StudentOutput() {
    }

    public StudentOutput(String id, String name, String email, String ra) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ra = ra;
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
}
