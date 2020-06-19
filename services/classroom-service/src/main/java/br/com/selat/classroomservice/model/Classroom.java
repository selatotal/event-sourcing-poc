package br.com.selat.classroomservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classroom {

    @Id
    private String id;
    private String name;

    public Classroom() {
    }

    public Classroom(String id, String name) {
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
