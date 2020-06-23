package br.com.selat.classroomservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Professor {

    @Id
    private String id;
    private String name;
    private String email;

    public Professor() {
    }

    public Professor(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
}
