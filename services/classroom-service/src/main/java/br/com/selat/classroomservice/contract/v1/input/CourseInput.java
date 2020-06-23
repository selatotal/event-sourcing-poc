package br.com.selat.classroomservice.contract.v1.input;

public class CourseInput {

    private String courseId;
    private String period;
    private String name;
    private Integer hours;
    private String program;

    public CourseInput() {
    }

    public CourseInput(String courseId, String period, String name, Integer hours, String program) {
        this.courseId = courseId;
        this.period = period;
        this.name = name;
        this.hours = hours;
        this.program = program;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
