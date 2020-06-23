package br.com.selat.classroomservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Classroom {

    @Id
    private String id;
    private String name;
    private Long dateStart;
    private Long dateEnd;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "classroom_professor",
            joinColumns = { @JoinColumn(name = "classroom_id")},
            inverseJoinColumns = { @JoinColumn(name = "professor_id")}
    )
    private Set<Professor> professors;

    @Transient
    private Set<String> professorList;

    public Classroom() {
    }

    public Classroom(String id, String name, Long dateStart, Long dateEnd, Set<Professor> professors) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.professors = professors;
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

    public Long getDateStart() {
        return dateStart;
    }

    public void setDateStart(Long start) {
        this.dateStart = start;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Long end) {
        this.dateEnd = end;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public Set<String> getProfessorList() {
        return professorList;
    }

    public void setProfessorList(Set<String> professorList) {
        this.professorList = professorList;
    }
}
