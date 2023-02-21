package com.cict.iamtracing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TopLoginsModel {

    String toDate;
    List<UserLoginModel> userLoginModels;

    List<String> forBlocking;
    List<String> forInvestigation;

    public static TopLoginsModel sample() {

        List<UserLoginModel> models = new ArrayList<>();
        models.add(UserLoginModel.builder()
                .email("c5x.fck@yopmail.com")
                .loginCount(237301)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());
        models.add(UserLoginModel.builder()
                .email("subscriber@convergeict.com.com")
                .loginCount(243)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(1)
                .build());
        models.add(UserLoginModel.builder()
                .email("jdelacruz@gmail.com")
                .loginCount(89)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(1)
                .build());
        models.add(UserLoginModel.builder()
                .email("hatdog@gmail.com")
                .loginCount(85)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());
        models.add(UserLoginModel.builder()
                .email("trabaholang@gmail.com")
                .loginCount(76)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(1)
                .build());
        models.add(UserLoginModel.builder()
                .email("gyuy@convergeict.com")
                .loginCount(70)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(1)
                .build());
        models.add(UserLoginModel.builder()
                .email("notasub@yahoo.com")
                .loginCount(13)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());
        models.add(UserLoginModel.builder()
                .email("notasub123123@yahoo.com")
                .loginCount(13)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());
        models.add(UserLoginModel.builder()
                .email("notasub123123@yahoo.com")
                .loginCount(13)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());
        models.add(UserLoginModel.builder()
                .email("not123123asub@yahoo.com")
                .loginCount(13)
                .userId("1239h2-ef7976s-2900sof-82")
                .accountNumberLinked(0)
                .build());


        TopLoginsModel topLoginsModel = TopLoginsModel.builder()
                .toDate("2022-09-26 15:00")
                .userLoginModels(models)
                .build();

        return topLoginsModel;
    }

    public List<String> getForBlocking() {

        if (forBlocking == null) {
            forBlocking = new ArrayList<>();
        }

        userLoginModels.forEach(login -> {
            if (login.getLoginCount() >= 300) {
                forBlocking.add(login.getEmail());
            }
        });

        return forBlocking;
    }

    public List<String> getForInvestigation() {

        if (forInvestigation == null) {
            forInvestigation = new ArrayList<>();
        }

        userLoginModels.forEach(login -> {
            if (login.getLoginCount() >= 100) {
                forInvestigation.add(login.getEmail());
            }
        });

        return forBlocking;
    }

}
