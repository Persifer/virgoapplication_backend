package com.application.virgo.utilities;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {

    public final static String CONTROLLER_OUTPUT = "application/json";
    public final static String USER_ROLE = "ROLE_USER";
    public final static String ADMIN_ROLE = "ROLE_ADMIN";

    public final static Long PAGE_SIZE = 50L;

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")
            .withZone(ZoneId.of("Europe/Rome")); //Europe/Rome (UTC+02:00)
}
