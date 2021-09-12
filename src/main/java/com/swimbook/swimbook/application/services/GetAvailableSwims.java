package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.GetAvailableSwimsPort;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.Swim;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableSwims implements GetAvailableSwimsPort {

    public List<Swim> execute(String date, String time, RequiredModel requiredModel) {
        List<Swim> availableSwims = requiredModel.getSwims();
        DateTime requestedDateTime = CreateDateTimeFromString.stringToDateTime((date + time));

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

}
