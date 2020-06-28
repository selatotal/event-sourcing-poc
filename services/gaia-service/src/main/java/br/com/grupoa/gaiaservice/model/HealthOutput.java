package br.com.grupoa.gaiaservice.model;

public class HealthOutput {

    private Integer status;

    public HealthOutput() {
    }

    public HealthOutput(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
