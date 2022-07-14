package com.cict.iamtracing.service;

import com.cict.iamtracing.entity.KeycloakUser;
import com.cict.iamtracing.repository.KeycloakUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class KeycloakUserService {

    @Autowired
    private KeycloakUserRepository keycloakUserRepository;

    public KeycloakUser findByUserId(String userId) {
        log.debug("FINDING USER[{}] DB:", userId);
        List<KeycloakUser> keycloakUsers = keycloakUserRepository.findByUserId(userId);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND USER with ID: {}", userId);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers.get(0) : null;
    }

}
