package co.il.treat.treatassign.service.impl;

import co.il.treat.treatassign.dto.Credentials;
import co.il.treat.treatassign.dto.EstimatedDogDto;
import co.il.treat.treatassign.dto.UserDto;
import co.il.treat.treatassign.model.EstimatedDog;
import co.il.treat.treatassign.model.User;
import co.il.treat.treatassign.repo.UserRepository;
import co.il.treat.treatassign.service.UserService;
import co.il.treat.treatassign.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserDto currentUser;

    private final UserRepository userRepository;

    private List<Object> currentDogList;

    @Autowired
    private UserMapper mapper;

    public static final String LIKE_EST = "like";
    public static final String DISLIKE_EST = "dislike";

    @Value("${animal.photo.size}")
    private String photoSize;

    /**
     * for login process imitation only
     */
    @Override
    public boolean isAuthorized() {
        return currentUser != null;
    }

    @Override
    public Set<EstimatedDogDto> getAllFavoriteDogs() {
        log.debug("Get favorite dogs for user {}", currentUser.getId());
        return filterDogsByEstimate(LIKE_EST);
    }

    @Override
    public Set<EstimatedDogDto> getDislikedDogs() {
        log.debug("Get disliked dogs for user {}", currentUser.getId());
        return filterDogsByEstimate(DISLIKE_EST);
    }

    @Override
    public UserDto getCurrentUser(){
        return this.currentUser;
    }

    private Set<EstimatedDogDto> filterDogsByEstimate(String estimate) {
        Set<EstimatedDogDto> resultSet = null;
        if (currentUser != null && estimate != null) {
            resultSet = currentUser.getEstimatedDogs()
                    .stream()
                    .filter(dog -> dog.getEstimate().equals(estimate))
                    .collect(Collectors.toSet());
            log.debug("{} dogs found with estimate {}", resultSet.size(), estimate);
        }
        return resultSet;
    }

    @Override
    @Transactional
    public Iterable<Object> addEstimateAndGetNewList(Integer dogId, Integer like) {
        log.info("add new estimate for dog {}", dogId);
        if (currentDogList != null && dogId != null && isAuthorized()) {
            String est = like > 0 ? LIKE_EST : DISLIKE_EST;
            EstimatedDogDto alreadyEstimated = findById(dogId, currentUser.getEstimatedDogs());
            if (alreadyEstimated!=null){
                alreadyEstimated.setEstimate(est);
                User user = mapper.toEntity(currentUser);
                currentUser = mapper.toDto(userRepository.save(user));
            } else {
                Map<String, Object> dogForEstimate = null;
                Iterator<Object> it = currentDogList.iterator();
                while (it.hasNext() && dogForEstimate == null) {
                    Map<String, Object> map = (Map<String, Object>) it.next();
                    if (map.get("id").equals(dogId)) {
                        dogForEstimate = map;
                        it.remove();
                    }
                }
                if (dogForEstimate != null) {
                    EstimatedDog estimatedDog = dogEntityBuild(dogForEstimate, est);
                    User user = mapper.toEntity(currentUser);
                    user.getEstimatedDogs().add(estimatedDog);
                    User savedUser = userRepository.save(user);
                    currentUser = mapper.toDto(savedUser);
                    log.debug("New estimated dog {} for user {} has been added", savedUser.getId(), estimatedDog.getId());
                }
            }
        } else throw new IllegalArgumentException("Wrong dog's id!");
        return currentDogList;
    }

    public EstimatedDogDto findById (Integer id, Set<EstimatedDogDto> dogDtos){
        return dogDtos.stream().filter(dog->dog.getId().equals(id)).findFirst().orElse(null);
    }

    private EstimatedDog dogEntityBuild(Map<String, Object> dogMap, String est) {
        return new EstimatedDog(
                (Integer) dogMap.get("id"),
                (String) dogMap.get("organization_id"),
                (String) ((Map<String, Object>) dogMap.get("breeds")).get("primary"),
                (String) dogMap.get("age"),
                (String) dogMap.get("gender"),
                (String) dogMap.get("size"),
                (String) dogMap.get("name"),
                (Long) dogMap.get("distance"),
                est,
                extractPhotoLinks((List<Map<String, String>>) dogMap.get("photos"))
        );
    }

    public String[] extractPhotoLinks(List<Map<String, String>> photos) {
        String[] linksArray = new String[photos.size()];
        List<String> linksList = photos
                .stream()
                .map(map -> map.get(photoSize))
                .toList();
        linksList.toArray(linksArray);
        return linksArray;
    }

    @Override
    @Transactional
    public void putCred(Credentials cred) {
        //User user = userRepository.findByLogin(cred.getLogin()).orElseThrow(IllegalArgumentException::new);
        //UserDto userDto = mapper.toDto(user);
        //for imitation
        User user = new User();
        user.setLogin(cred.getLogin());
        user.setPassword(cred.getPassword());
        user.setName("Vasya");
        this.currentUser = mapper.toDto(userRepository.save(user));
    }

    @Override
    public void setCurrentDogList(List<Object> currentDogList) {
        this.currentDogList = currentDogList;
    }
}
