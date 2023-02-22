package com.cict.iamtracing.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserGroupMembershipId implements Serializable {

    private String keycloakUser;
    private String keycloakGroup;

}
