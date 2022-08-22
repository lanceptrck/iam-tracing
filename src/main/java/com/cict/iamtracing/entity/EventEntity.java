package com.cict.iamtracing.entity;

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
@Table(name = "EVENT_ENTITY", schema = "keycloak")
public class EventEntity {

    @Id
    @Column(name = "ID")
    private String eventId;

    @Column(name = "EVENT_TIME")
    private Timestamp eventTime;

    @Column(name = "EVENT_TYPE")
    private String eventType;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnoreProperties("eventEntities")
    private KeycloakUser keycloakUser;

}
