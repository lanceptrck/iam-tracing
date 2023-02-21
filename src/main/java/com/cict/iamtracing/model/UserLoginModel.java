package com.cict.iamtracing.model;

import com.cict.iamtracing.entity.LoginReport;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UserLoginModel {

    String email;
    Integer accountNumberLinked;
    String userId;
    Integer loginCount;

    public static UserLoginModel of(LoginReport loginReport){
        return UserLoginModel.builder()
                .email(loginReport.getEmail())
                .loginCount(loginReport.getLoginCount())
                .userId(loginReport.getUserId())
                .build();
    }

}
