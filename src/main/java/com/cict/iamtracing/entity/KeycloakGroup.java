package com.cict.iamtracing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "KEYCLOAK_GROUP", schema = "keycloak")
public class KeycloakGroup {

    @Id
    @Column(name = "ID")
    @JsonIgnore
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REALM_ID")
    private String realm;

}
