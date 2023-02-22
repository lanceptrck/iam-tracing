package com.cict.iamtracing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@IdClass(UserGroupMembershipId.class)
@Table(name = "USER_GROUP_MEMBERSHIP", schema = "keycloak")
public class UserGroupMembership {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnoreProperties(value = {"attributes", "groupId"})
    @JsonIgnore
    private KeycloakUser keycloakUser;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private KeycloakGroup keycloakGroup;

}
