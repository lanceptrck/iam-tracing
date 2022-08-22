package com.cict.iamtracing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "USER_ATTRIBUTE", schema = "keycloak")
public class UserAttribute {

    @Id
    @Column(name = "ID")
    @JsonIgnore
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnoreProperties("attributes")
    @JsonIgnore
    private KeycloakUser keycloakUser;


}
