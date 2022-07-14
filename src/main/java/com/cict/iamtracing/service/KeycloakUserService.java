package com.cict.iamtracing.service;

import com.cict.iamtracing.entity.AccountDetails;
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

    public List<AccountDetails> findKeycloakUsersByAccountNumber(String accountNumber) {
        log.debug("FINDING USERS WITH ACCOUNT NUMBER: {}", accountNumber);
        List<AccountDetails> keycloakUsers = keycloakUserRepository.findKeycloakUsersByAccountNumber(accountNumber);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND {} USER(S) with ACCOUNT NUMBER: {}", keycloakUsers.size(), accountNumber);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers : null;

    }

    public List<KeycloakUser> findKeycloakUserByNames(String firstName, String lastName) {
        log.debug("FINDING USERS WITH NAMES: {} {}", firstName, lastName);

        String firstNameQuery = '%' + firstName == null ? "" : firstName + '%';
        String lastNameQuery = '%' + lastName == null ? "" : lastName + '%';

        List<KeycloakUser> keycloakUsers = keycloakUserRepository.findKeycloakUserByNames(firstNameQuery, lastNameQuery);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND {} USER(S) with NAME: {} {}", keycloakUsers.size(), firstName, lastName);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers : null;
    }

}
