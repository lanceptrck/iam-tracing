package com.cict.iamtracing.controller;

import com.cict.iamtracing.entity.RegisteredUsersReport;
import com.cict.iamtracing.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

import java.util.List;

import static com.cict.iamtracing.response.GenericResponse.constructResponse;

@RestController
@Slf4j
@RequestMapping("v1/reports")
public class RegisteredUsersController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @GetMapping("registered-users")
    public @ResponseBody
    ResponseEntity<?> getRegisteredUsersFrom(@RequestParam String fromDate, @RequestParam String toDate) {

        try {
            List<RegisteredUsersReport> registeredUsers = keycloakUserService.getRegisteredUsersBetween(fromDate, toDate);

            if (registeredUsers != null) {
                return ResponseEntity.ok(registeredUsers);
            } else {
                return new ResponseEntity<>(constructResponse(null,
                        "No registered user(s) with given identifier.", false), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().body(constructResponse(null,
                "Something went wrong with the service", false));
    }
}
