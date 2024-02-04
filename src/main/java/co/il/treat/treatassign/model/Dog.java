package co.il.treat.treatassign.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode
public class Dog {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String organization_id;

    @Column
    private String breed;

    @Column
    private String age;

    @Column
    private String gender;

    @Column
    private String size;

    @Column
    private String name;

    @Column
    private String[] photos;

    @Column
    private Long distance;

    public Dog() {
    }

    public Dog(Integer id, String organization_id, String breed, String age, String gender, String size,
               String name, String[] photos, Long distance) {
        this.id = id;
        this.organization_id = organization_id;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.name = name;
        this.photos = photos;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }
}
