package com.cict.iamtracing.repository;

import com.cict.iamtracing.entity.AccountDetails;
import com.cict.iamtracing.entity.KeycloakUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeycloakUserRepository extends JpaRepository<KeycloakUser, String> {

    List<KeycloakUser> findByUserId(String userId);

    @Query(value = "SELECT \n" +
            "    ue.ID as 'id',\n" +
            "    ue.EMAIL as 'email',\n" +
            "    ue.FIRST_NAME as 'firstName',\n" +
            "    ue.LAST_NAME as 'lastName',\n" +
            "    ua.VALUE AS 'accountNumber'\n" +
            "FROM\n" +
            "    USER_ENTITY ue\n" +
            "        INNER JOIN\n" +
            "    USER_ATTRIBUTE ua ON ue.ID = ua.USER_ID\n" +
            "WHERE\n" +
            "    ua.NAME = 'accountNumber'\n" +
            "        AND ua.VALUE = :accountNumber", nativeQuery = true)
    List<AccountDetails> findKeycloakUsersByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(value = "SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    keycloak.USER_ENTITY\n" +
            "WHERE\n" +
            "    FIRST_NAME LIKE :firstName\n" +
            "        AND LAST_NAME LIKE :lastName\n", nativeQuery = true)
    List<KeycloakUser> findKeycloakUserByNames(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
