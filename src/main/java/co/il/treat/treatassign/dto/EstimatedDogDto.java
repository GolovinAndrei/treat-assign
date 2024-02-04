package co.il.treat.treatassign.dto;

import lombok.Data;


public class EstimatedDogDto extends DogDto{

    private String estimate;

    public EstimatedDogDto() {
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }
}
