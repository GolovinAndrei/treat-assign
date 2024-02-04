package co.il.treat.treatassign.web;

import co.il.treat.treatassign.dto.Credentials;
import co.il.treat.treatassign.dto.UserDto;
import co.il.treat.treatassign.model.User;
import co.il.treat.treatassign.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static co.il.treat.treatassign.web.RestConstants.LOGIN_PATH;

/**
 * LogIn process imitation
 */
@RequiredArgsConstructor
@RestController
public class LogIn {

    private final UserServiceImpl userService;

    @PostMapping(LOGIN_PATH)
    ResponseEntity<Object> logIn (@RequestBody Credentials cred){
        userService.putCred(cred);
        return ResponseEntity.ok().build();
    }
}
