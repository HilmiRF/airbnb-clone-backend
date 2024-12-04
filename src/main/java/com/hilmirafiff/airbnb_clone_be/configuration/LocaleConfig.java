package com.hilmirafiff.airbnb_clone_be.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class LocaleConfig {
    @PostConstruct
    public void initTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Jakarta")));
    }
}
