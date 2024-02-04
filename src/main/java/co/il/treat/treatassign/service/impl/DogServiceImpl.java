package co.il.treat.treatassign.service.impl;

import co.il.treat.treatassign.api.ExternalApiProxyFeign;
import co.il.treat.treatassign.dto.DogDto;
import co.il.treat.treatassign.dto.EstimatedDogDto;
import co.il.treat.treatassign.model.Dog;
import co.il.treat.treatassign.model.EstimatedDog;
import co.il.treat.treatassign.repo.DogRepository;
import co.il.treat.treatassign.service.DogService;
import co.il.treat.treatassign.service.UserService;
import co.il.treat.treatassign.service.mapper.EstimatedDogMapper;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DogServiceImpl implements DogService {

    private final ExternalApiProxyFeign apiClient;

    private final UserService userService;

    private final DogRepository dogRepository;

    private final EstimatedDogMapper mapper;


    @Value("${animal.type.permanent}")
    private String animalType;

    @Value("${delivery.size.max}")
    private int limit;


    private static final String KEY_FOR_DOGS_FROM_EXTERNAL_DATA = "animals";

    @Override
    public List<Object> getAllDogsFromApi(String organization, String gender, String location, String breed, int page) throws AuthException {
        log.info("Request for dogs with params: organization {}, gender {}, location {},  breed {}, page {}, showDisliked {}",
                organization, gender, location, breed, page);
        Object dogsFromApi = apiClient.getAllDogs(organization, gender, location, breed, animalType, page, limit);
        Map<String, List<Object>> mappedDogs = (Map<String, List<Object>>) dogsFromApi;
        List<Object> dogs = mappedDogs.get(KEY_FOR_DOGS_FROM_EXTERNAL_DATA);
            if (userService.isAuthorized()) {
                Set<Integer> alreadyEstimated = userService.getCurrentUser().getEstimatedDogs()
                        .stream()
                        .map(DogDto::getId)
                        .collect(Collectors.toSet());
                dogs= dogs
                        .stream()
                        .filter(dog -> {
                            Map<String, Object> map = (Map<String, Object>) dog;
                            return !alreadyEstimated.contains((Integer) map.get("id"));
                        })
                        .collect(Collectors.toList());
            } else throw new AuthException("You should to login!");
        log.debug("was found {} records", dogs.size());
        userService.setCurrentDogList(dogs);
        return dogs;
    }

    public List<EstimatedDogDto> getAllDogsWithEstimates () {
        List<EstimatedDog> dogs = dogRepository.findAll();
        log.info("{} dogs with estimates have been found", dogs.size());
        return mapper.toListDto(dogs);
    }


}
