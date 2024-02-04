package co.il.treat.treatassign.web;

import co.il.treat.treatassign.dto.EstimatedDogDto;
import co.il.treat.treatassign.service.DogService;
import co.il.treat.treatassign.service.UserService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.il.treat.treatassign.web.RestConstants.*;


@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class DogsRest {

    private final DogService dogService;

    private final UserService userService;

    /**
     * Get list of dogs according to filter params
     */
    @GetMapping(PATH_GET_ALL_DOGS_BY_PAGES)
    ResponseEntity<Iterable<Object>> getAllDogs(
            @RequestParam(required = false) String organization,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String breed,
            @RequestParam(required = true) @Positive int page
            ) {
        log.info("Request for all dogs with params");
        Iterable<Object> listOfDogs;
        try {
            listOfDogs = dogService.getAllDogsFromApi(organization, gender, location, breed, page);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(listOfDogs);
    }

    /**
     * Add like or dislike
     *
     * @param id   - id of dog for estimate
     * @param like - like/dislike param, 0/1
     * @return updated list of dogs
     */
    @PostMapping(PATH_ADD_ESTIMATE)
    ResponseEntity<Iterable<Object>> addEstimate(
            @RequestParam Integer id,
            @RequestParam @Min(0) @Max(value = 1, message = "0 or 1 only!") Integer like) {
        log.info("Adding of like or dislike for particular dog");
        if (!userService.isAuthorized()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(userService.addEstimateAndGetNewList(id, like));
    }

    /**
     * Get list of favorite dogs
     */
    @GetMapping(PATH_GET_FAVORITE)
    ResponseEntity<Iterable<EstimatedDogDto>> getFavorite() {
        log.info("Request for favorite dogs list");
        if (!userService.isAuthorized()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(userService.getAllFavoriteDogs());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCommonException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}