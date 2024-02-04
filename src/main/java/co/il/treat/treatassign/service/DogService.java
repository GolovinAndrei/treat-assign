package co.il.treat.treatassign.service;

import jakarta.security.auth.message.AuthException;


import java.util.List;

public interface DogService {

    List<Object> getAllDogsFromApi(String organization, String gender, String location, String breed, int page) throws AuthException;
}
