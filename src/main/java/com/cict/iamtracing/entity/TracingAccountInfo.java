package com.cict.iamtracing.entity;

import java.util.List;

public interface TracingAccountInfo {

    String getUserId();

    String getEmail();

    String getFirstName();

    String getLastName();

    List<UserAttribute> getAttributes();

    List<UserGroupMembership> getUserGroupMemberships();

    Boolean getEnabled();
}
