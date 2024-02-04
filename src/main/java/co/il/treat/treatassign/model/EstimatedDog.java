package co.il.treat.treatassign.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "dogs")
@Entity
public class EstimatedDog extends Dog {


    @Column
    private String estimate;

    public EstimatedDog(Integer id, String organization_id, String breed, String age, String gender, String size, String name,
                        Long distance, String estimate, String[]photos) {

        super(id, organization_id, breed, age, gender, size, name, photos, distance);
        this.estimate = estimate;
    }

    public EstimatedDog() {
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }
}

