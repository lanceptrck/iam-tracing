package com.cict.iamtracing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "USER_ENTITY", schema = "keycloak")
public class KeycloakUser {

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @OneToMany(mappedBy = "keycloakUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"keycloakUser"})
    private List<EventEntity> events;

    @OneToMany(mappedBy = "keycloakUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"keycloakUser"})
    private List<UserAttribute> attributes;

    @JsonIgnore
    private List<EventEntity> getEvents() {
        return events;
    }

}
