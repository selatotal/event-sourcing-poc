package br.com.selat.classroomservice.contract.v1.input;

import java.util.Set;

public class ClassroomInput {

    private String name;
    private Long dateStart;
    private Long dateEnd;
    private Set<String> professors;

    public ClassroomInput() {
    }

    public ClassroomInput(String name, Long dateStart, Long dateEnd, Set<String> professors) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.professors = professors;
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

    public void setDateStart(Long dateStart) {
        this.dateStart = dateStart;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Set<String> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<String> professors) {
        this.professors = professors;
    }
}
