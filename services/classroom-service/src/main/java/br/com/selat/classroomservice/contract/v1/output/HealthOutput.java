package br.com.selat.classroomservice.contract.v1.output;

public class HealthOutput {

    private int status;

    public HealthOutput() {
    }

    public HealthOutput(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
