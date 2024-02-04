package co.il.treat.treatassign.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode
public class UserDto {

    private Long id;

    private String login;

    private String password;

    private String name;

    private Set<EstimatedDogDto> estimatedDogs = new HashSet<>();

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EstimatedDogDto> getEstimatedDogs() {
        return estimatedDogs;
    }

    public void setEstimatedDogs(Set<EstimatedDogDto> estimatedDogs) {
        this.estimatedDogs = estimatedDogs;
    }
}
