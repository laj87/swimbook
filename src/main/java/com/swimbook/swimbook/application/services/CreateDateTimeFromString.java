package com.swimbook.swimbook.application.services;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Utility class, creates a DateTime instance from a String input.  String input must be:
 * YYYYMMDDhhmm
 */
public final class CreateDateTimeFromString {

    /**
     * Static method for converting a String date time into a DateTime instance
     *
     * @param dateTimeString format must be YYYYMMDDhhmm
     * @return
     */
    public static DateTime stringToDateTime(String dateTimeString) {
        StringBuilder tokens = new StringBuilder(dateTimeString);

        int YYYY = Integer.parseInt(tokens.substring(0, 4));
        int MM = Integer.parseInt(tokens.substring(4, 6));
        int DD = Integer.parseInt(tokens.substring(6, 8));
        int hh = Integer.parseInt(tokens.substring(8, 10));
        int mm = Integer.parseInt(tokens.substring(10, 12));

        return new DateTime(YYYY, MM, DD, hh, mm);
    }
}

