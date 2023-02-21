package com.cict.iamtracing.repository;

import com.cict.iamtracing.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeycloakUserRepository extends JpaRepository<KeycloakUser, String> {

    List<TracingAccountInfo> findByUserId(String userId);

    List<TracingAccountInfo> findTracingInfoByEmail(String email);

    @Query(value = "SELECT \n" +
            "    ue.ID as 'id',\n" +
            "    ue.EMAIL as 'email',\n" +
            "    ue.FIRST_NAME as 'firstName',\n" +
            "    ue.LAST_NAME as 'lastName',\n" +
            "    ua.VALUE AS 'accountNumber'\n" +
            "FROM\n" +
            "    USER_ENTITY ue\n" +
            "        LEFT JOIN\n" +
            "    USER_ATTRIBUTE ua ON ue.ID = ua.USER_ID\n" +
            "WHERE\n" +
            "    ua.NAME = 'accountNumber'\n" +
            "        AND ua.VALUE = :accountNumber", nativeQuery = true)
    List<AccountDetails> findKeycloakUsersByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(value = "SELECT \n" +
            "    u.ID as 'userId',\n" +
            "    u.FIRST_NAME as 'firstName',\n" +
            "    u.LAST_NAME as 'lastName',\n" +
            "    u.EMAIL as 'email',\n" +
            "    u.ENABLED as 'enabled'\n" +
            "FROM\n" +
            "    keycloak.USER_ENTITY u\n" +
            "WHERE\n" +
            "    FIRST_NAME LIKE :firstName\n" +
            "        AND LAST_NAME LIKE :lastName\n", nativeQuery = true)
    List<TracingAccountInfo> findKeycloakUserByNames(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT \n" +
            "    u.FIRST_NAME as 'firstName',\n" +
            "    u.LAST_NAME as 'lastName',\n" +
            "    u.EMAIL as 'email',\n" +
            "    ua.VALUE AS 'accountNumber',\n" +
            "    ee.CLIENT_ID AS 'client',\n" +
            "    DATE_FORMAT(FROM_UNIXTIME(ee.EVENT_TIME * POWER(10, 9 - FLOOR(LOG10(ee.EVENT_TIME)))),\n" +
            "            '%m/%d/%Y') AS 'registeredDate',\n" +
            "    DATE_FORMAT(FROM_UNIXTIME(ee.EVENT_TIME * POWER(10, 9 - FLOOR(LOG10(ee.EVENT_TIME)))),\n" +
            "            '%H:%i') AS 'registeredTime'\n" +
            "FROM\n" +
            "    keycloak.USER_ENTITY u\n" +
            "        LEFT JOIN\n" +
            "    keycloak.EVENT_ENTITY ee ON u.ID = ee.USER_ID\n" +
            "        LEFT JOIN\n" +
            "    keycloak.USER_ATTRIBUTE ua ON u.ID = ua.USER_ID\n" +
            "WHERE\n" +
            "    ee.TYPE = 'REGISTER'\n" +
            "        AND ee.EVENT_TIME BETWEEN (SELECT ROUND(UNIX_TIMESTAMP(TIMESTAMP( :fromDate )) * 1000)) AND (SELECT ROUND(UNIX_TIMESTAMP(TIMESTAMP( :toDate )) * 1000)) \n" +
            "        AND ua.NAME = 'accountNumber'\n" +
            "ORDER BY ee.EVENT_TIME desc", nativeQuery = true)
    List<RegisteredUsersReport> findRegisteredUsersBetween(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT \n" +
            "    u.EMAIL AS 'email',\n" +
            "    u.ID AS 'userId',\n" +
            "    COUNT(*) AS 'loginCount'\n" +
            "FROM\n" +
            "    keycloak.USER_ENTITY u\n" +
            "        INNER JOIN\n" +
            "    keycloak.EVENT_ENTITY ee ON u.ID = ee.USER_ID\n" +
            "WHERE\n" +
            "    ee.TYPE = 'CODE_TO_TOKEN'\n" +
            "        AND ee.EVENT_TIME BETWEEN ROUND(UNIX_TIMESTAMP(TIMESTAMP( :fromDate )) * 1000) AND  ROUND(UNIX_TIMESTAMP(TIMESTAMP( :toDate )) * 1000)\n" +
            "GROUP BY u.EMAIL , u.ID\n" +
            "ORDER BY loginCount DESC;", nativeQuery = true)
    List<LoginReport> findLoginCountByUsers(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT \n" +
            "    kg.name AS 'GROUP'\n" +
            "FROM\n" +
            "    USER_ENTITY ue\n" +
            "        LEFT JOIN\n" +
            "    USER_GROUP_MEMBERSHIP ugm ON ue.ID = ugm.USER_ID\n" +
            "        LEFT JOIN\n" +
            "    KEYCLOAK_GROUP kg ON kg.ID = ugm.GROUP_ID\n" +
            "        LEFT JOIN\n" +
            "    REALM r ON r.ID = ue.REALM_ID\n" +
            "WHERE\n" +
            "    ue.EMAIL = :email \n" +
            "        AND r.NAME = 'Converge'", nativeQuery = true)
    TracingAccountInfo findGroupByEmail(@Param("email") String email);

    @Query(value = "SELECT \n" +
            "    ue.*\n" +
            "FROM\n" +
            "    USER_ENTITY ue\n" +
            "        LEFT JOIN\n" +
            "    USER_GROUP_MEMBERSHIP ugm ON ue.ID = ugm.USER_ID\n" +
            "        LEFT JOIN\n" +
            "    KEYCLOAK_GROUP kg ON kg.ID = ugm.GROUP_ID\n" +
            "        LEFT JOIN\n" +
            "    REALM r ON r.ID = ue.REALM_ID\n" +
            "WHERE\n" +
            "    ue.EMAIL = :email \n" +
            "        AND r.NAME = 'Converge';", nativeQuery = true)
    KeycloakUser findByEmail(@Param("email") String email);

    @Query(value = "SELECT \n" +
            "    ue.*\n" +
            "FROM\n" +
            "    USER_ENTITY ue\n" +
            "        LEFT JOIN\n" +
            "    USER_GROUP_MEMBERSHIP ugm ON ue.ID = ugm.USER_ID\n" +
            "        LEFT JOIN\n" +
            "    KEYCLOAK_GROUP kg ON kg.ID = ugm.GROUP_ID\n" +
            "        LEFT JOIN\n" +
            "    REALM r ON r.ID = ue.REALM_ID\n" +
            "WHERE\n" +
            "    ue.EMAIL in :emails \n" +
            "        AND r.NAME = 'Converge';", nativeQuery = true)
    List<KeycloakUser> findByEmails(@Param("emails") List<String> emails);





}
