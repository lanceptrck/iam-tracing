package com.cict.iamtracing.service;

import com.cict.iamtracing.entity.*;
import com.cict.iamtracing.repository.KeycloakUserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class KeycloakUserService {

    @Autowired
    private KeycloakUserRepository keycloakUserRepository;

    public void deactivateUser(String email){
        log.debug("DEACTIVATING USER: {}", email);

        KeycloakUser keycloakUser = keycloakUserRepository.findByEmail(email);

        if(keycloakUser != null){
            keycloakUser.setEnabled(false);
            keycloakUser = keycloakUserRepository.save(keycloakUser);
            log.debug("DEACTIVATED USER: {}-{}", keycloakUser.getEmail());
        } else {
            log.debug("USER NOT FOUND");
        }

    }

    @Async("processExecutor")
    public void deactivateUsers(List<String> emails){

/*        List<KeycloakUser> keycloakUsers = keycloakUserRepository.findByEmails(emails);

        if(keycloakUsers != null && !keycloakUsers.isEmpty()){
            for(KeycloakUser user : keycloakUsers){
                user.setEnabled(false);
                user = keycloakUserRepository.save(user);
                log.debug("DEACTIVATED USER - {} ? enabled {}", user.getEmail(), user.getEnabled());
            }
        }*/

     emails.forEach(email -> {
            KeycloakUser keycloakUser = keycloakUserRepository.findByEmail(email);

            if(keycloakUser != null){
                keycloakUser.setEnabled(false);
                keycloakUser = keycloakUserRepository.save(keycloakUser);
                log.debug("DEACTIVATED USER - {} ? enabled {}", keycloakUser.getEmail(), keycloakUser.getEnabled());
            } else {
                log.debug("USER {} NOT FOUND", email);
            }

        });

    }

    public TracingAccountInfo findByUserId(String userId) {
        log.debug("FINDING USER[{}] DB:", userId);
        List<TracingAccountInfo> keycloakUsers = keycloakUserRepository.findByUserId(userId);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND USER with ID: {}", userId);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers.get(0) : null;
    }

    public List<TracingAccountInfo> findByEmail(String email){
        log.debug("FINDING USER[{}]: ", email);
        List<TracingAccountInfo> keycloakUsers = keycloakUserRepository.findTracingInfoByEmail(email);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND {} USER(S) with EMAIL: {}", keycloakUsers.size(), email);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers : null;
    }

    public List<AccountDetails> findKeycloakUsersByAccountNumber(String accountNumber) {
        log.debug("FINDING USERS WITH ACCOUNT NUMBER: {}", accountNumber);
        List<AccountDetails> keycloakUsers = keycloakUserRepository.findKeycloakUsersByAccountNumber(accountNumber);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND {} USER(S) with ACCOUNT NUMBER: {}", keycloakUsers.size(), accountNumber);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers : null;

    }

    public List<TracingAccountInfo> findKeycloakUserByNames(String firstName, String lastName) {
        log.debug("FINDING USERS WITH NAMES: {} {}", firstName, lastName);

        String firstNameQuery = '%' + firstName == null ? "" : firstName + '%';
        String lastNameQuery = '%' + lastName == null ? "" : lastName + '%';

        List<TracingAccountInfo> keycloakUsers = keycloakUserRepository.findKeycloakUserByNames(firstNameQuery, lastNameQuery);

        if (keycloakUsers.size() > 0) {
            log.debug("FOUND {} USER(S) with NAME: {} {}", keycloakUsers.size(), firstName, lastName);
        }

        return !keycloakUsers.isEmpty() ? keycloakUsers : null;
    }

    public List<RegisteredUsersReport> getRegisteredUsersBetween(String fromDate, String toDate) {
        log.debug("FINDING REGISTERED USERS BETWEEN: {} and {}", fromDate, toDate);

        List<RegisteredUsersReport> registerUsers = keycloakUserRepository.findRegisteredUsersBetween(fromDate, toDate);

        if (registerUsers.size() > 0) {
            log.debug("FOUND {} REGISTERED USER(S) with from {} to {}", registerUsers.size(), fromDate, toDate);
        }

        return !registerUsers.isEmpty() ? registerUsers : null;
    }

    public List<LoginReport> getLoginReportsBetween(String fromDate, String toDate){
        log.debug("FINDING LOGGED IN USERS DATE BETWEEN: {} and {}", fromDate, toDate);

        List<LoginReport> loginReports = keycloakUserRepository.findLoginCountByUsers(fromDate, toDate);

        if(loginReports.size() > 0 && !loginReports.isEmpty()) {
            log.debug("RETRIEVED LOGGED IN USERS DATA");
            return loginReports.size() >= 10 ? loginReports.subList(0, 10) : loginReports;
        }

        return !loginReports.isEmpty() ? loginReports : null;
    }


}
