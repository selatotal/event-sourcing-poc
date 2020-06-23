package br.com.selat.studentservice.contract.v1.input;

public class StudentInput {

    private String name;
    private String email;
    private String ra;

    public StudentInput() {
    }

    public StudentInput(String name, String email, String ra) {
        this.name = name;
        this.email = email;
        this.ra = ra;
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
