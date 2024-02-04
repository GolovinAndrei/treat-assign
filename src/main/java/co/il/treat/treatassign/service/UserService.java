package co.il.treat.treatassign.service;

import co.il.treat.treatassign.dto.Credentials;
import co.il.treat.treatassign.dto.EstimatedDogDto;
import co.il.treat.treatassign.dto.UserDto;

import java.util.List;
import java.util.Set;


public interface UserService {

    void putCred (Credentials cred);
    boolean isAuthorized ();
    Set<EstimatedDogDto> getAllFavoriteDogs();
    Set<EstimatedDogDto> getDislikedDogs();
    Iterable<Object> addEstimateAndGetNewList(Integer dogId, Integer like);
    void setCurrentDogList(List<Object> currentDogList);
    UserDto getCurrentUser();

}
