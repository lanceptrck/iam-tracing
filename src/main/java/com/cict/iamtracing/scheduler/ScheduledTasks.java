package com.cict.iamtracing.scheduler;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.cict.iamtracing.entity.LoginReport;
import com.cict.iamtracing.model.UserLoginModel;
import com.cict.iamtracing.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    KeycloakUserService keycloakUserService;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void reportLoginsEveryCertainMinutes() {
        LocalDateTime ldt = LocalDateTime.now();
        String fromDate = ldt.minus(Duration.ofHours(1)).format(dtf);
        String toDate = ldt.format(dtf);

        List<LoginReport> loginReportList = keycloakUserService.getLoginReportsBetween(fromDate, toDate);

        log.debug("EMAIL > USER ID > LOGIN COUNT");
        loginReportList.forEach(login -> {
            log.debug("{}", UserLoginModel.of(login));
        });



    }

}