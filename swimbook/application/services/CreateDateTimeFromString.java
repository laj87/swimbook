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
     * @param dateTime format must be YYYYMMDDhhmm
     * @return
     */
    public static DateTime stringToDateTime(String dateTime) {
        char[] dateTimeTokens = dateTime.toCharArray();
        int YYYY = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,0,3).toString());
        int MM = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,4,5).toString());
        int DD = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,6,7).toString());
        int HH = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,8,9).toString());
        int mm = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,10,11).toString());

        return new DateTime(YYYY,MM,DD,HH,mm);
    }
}
