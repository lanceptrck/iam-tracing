package com.cict.iamtracing.controller;

import com.cict.iamtracing.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cict.iamtracing.response.GenericResponse.constructResponse;

@RestController
@Slf4j
@RequestMapping("v1/tracing/uam")
public class AgentController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @DeleteMapping("/sales-partners")
    public ResponseEntity<?> deactivateUser(@RequestParam String email) {

        if(StringUtils.isNotBlank(email)){
            keycloakUserService.deactivateUser(email);
            return ResponseEntity.ok(constructResponse(null, "Successfully deactivated user", true));
        }

        return new ResponseEntity<>(constructResponse(null, "Email not found.", false), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/bulk-sales-partners")
    public ResponseEntity<?> deactivateUsers(@RequestBody List<String> emails) {

        if(emails != null && !emails.isEmpty()){
            keycloakUserService.deactivateUsers(emails);
            return ResponseEntity.ok(constructResponse(null, "Successfully deactivated user", true));
        }

        return new ResponseEntity<>(constructResponse(null, "Email not found.", false), HttpStatus.BAD_REQUEST);
    }




}
