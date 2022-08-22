package com.cict.iamtracing.controller;

import com.cict.iamtracing.entity.AccountDetails;
import com.cict.iamtracing.entity.KeycloakUser;
import com.cict.iamtracing.entity.TracingAccountInfo;
import com.cict.iamtracing.response.GenericResponse;
import com.cict.iamtracing.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cict.iamtracing.response.GenericResponse.constructResponse;

@RestController
@Slf4j
@RequestMapping("v1/tracing/users")
public class KeycloakUserController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @GetMapping("")
    public @ResponseBody
    ResponseEntity<?> getKeycloakUser(@RequestParam String operation,
                                      @RequestParam(required = false) String userId,
                                      @RequestParam(required = false) String accountNumber,
                                      @RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName) {
        try {
            if (operation.equalsIgnoreCase("userid") && userId != null) {
                TracingAccountInfo keycloakUser = keycloakUserService.findByUserId(userId);
                if (keycloakUser != null) {
                    return ResponseEntity.ok(
                            constructResponse(keycloakUser,
                                    "Successfully fetched user", true));
                } else {
                    return new ResponseEntity<>(constructResponse(null,
                            "User(s) with given identifier not found.", false), HttpStatus.NOT_FOUND);
                }
            } else if (operation.equalsIgnoreCase("accountnumber") && accountNumber != null) {
                List<AccountDetails> accountDetails = keycloakUserService.findKeycloakUsersByAccountNumber(accountNumber);

                if (accountDetails != null) {
                    return ResponseEntity.ok(constructResponse(accountDetails, "Successfully fetched user(s)", true));
                } else {
                    return new ResponseEntity<>(constructResponse(null,
                            "User(s) with given identifier not found.", false), HttpStatus.NOT_FOUND);
                }
            } else if(operation.equalsIgnoreCase("name") && (firstName != null || lastName != null)){
                List<TracingAccountInfo> keycloakUsers = keycloakUserService.findKeycloakUserByNames(firstName, lastName);

                if (keycloakUsers != null) {
                    return ResponseEntity.ok(constructResponse(keycloakUsers, "Successfully fetched user(s)", true));
                } else {
                    return new ResponseEntity<>(constructResponse(null,
                            "User(s) with given identifier not found.", false), HttpStatus.NOT_FOUND);
                }

            }
            else {
                return ResponseEntity.badRequest().body(constructResponse(null,
                        "Given operation parameter is invalid", false));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().body(constructResponse(null,
                "Something went wrong with the service", false));
    }

}
