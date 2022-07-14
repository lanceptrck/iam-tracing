package com.cict.iamtracing.repository;

import com.cict.iamtracing.entity.KeycloakUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeycloakUserRepository extends JpaRepository<KeycloakUser, String> {

    List<KeycloakUser> findByUserId(String userId);

}
