package br.com.selat.classroomservice.contract.v1.output;

import java.util.Set;

public class ClassroomOutput {

    private String id;
    private String name;
    private Long dateStart;
    private Long dateEnd;
    private Set<String> professors;

    public ClassroomOutput() {
    }

    public ClassroomOutput(String id, String name, Long dateStart, Long dateEnd, Set<String> professors) {
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
