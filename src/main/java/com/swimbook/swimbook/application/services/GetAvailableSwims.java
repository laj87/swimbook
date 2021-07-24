package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.GetAvailableSwimsPort;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.Swim;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableSwims implements GetAvailableSwimsPort {

    public List<Swim> execute(String date, String time, RequiredModel requiredModel) {
        List<Swim> availableSwims = requiredModel.getSwims();
        DateTime requestedDateTime = this.createDateTimeInstance(date, time);

        //Check swim availability.
        for(Swim each : availableSwims) {
            for(Booking swimBookings : each.getBookings()) {
                if(swimBookings.getBookingDuration().contains(requestedDateTime)) {
                    availableSwims.remove(each);
                    break;
                }
            }
        }

        //Check swim status.
        for(Swim each : availableSwims) {
            if((!each.getSwimStatus().getStatus())) {
                availableSwims.remove(each);
                break;
            }
        }

        return availableSwims;
    }

    /**
     * Helper method for creating the datetime instance using the string date and time arguments
     * @param date
     * @param time
     * @return
     */
    private DateTime createDateTimeInstance(String date, String time) {
        char[] dateTimeTokens = (date + time).toCharArray();
        int YYYY = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,0,3).toString());
        int MM = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,4,5).toString());
        int DD = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,6,7).toString());
        int HH = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,8,9).toString());
        int mm = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,10,11).toString());

        return new DateTime(YYYY,MM,DD,HH,mm);
    }
}
