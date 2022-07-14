package com.cict.iamtracing.controller;

import com.cict.iamtracing.entity.KeycloakUser;
import com.cict.iamtracing.response.GenericResponse;
import com.cict.iamtracing.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cict.iamtracing.response.GenericResponse.constructResponse;

@RestController
@Slf4j
@RequestMapping("v1/tracing/users")
public class KeycloakUserController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @GetMapping("")
    public @ResponseBody
    ResponseEntity<?> getKeycloakUser(@RequestParam String userId) {
        try {
            KeycloakUser keycloakUser = keycloakUserService.findByUserId(userId);
            if (keycloakUser != null) {
                return ResponseEntity.ok(
                        constructResponse(keycloakUser,
                                "Successfully fetched user", true));
            } else {
                return new ResponseEntity<>(constructResponse(null,
                        "User with given ID not found.", false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().body(constructResponse(null,
                "Something went wrong with the service", false));
    }

}
