package com.github.khangzxrr.config;

import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import java.util.Arrays;
import java.util.List;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final double FIRST_PAYMENT_PERCENT = 80;

    public static final List<RequestProgressType> REQUEST_PROGRESS_REPORT_TYPES = Arrays.asList(
        RequestProgressType.REPORT_1,
        RequestProgressType.REPORT_2,
        RequestProgressType.REPORT_3,
        RequestProgressType.REPORT_4
    );

    private Constants() {}
}
