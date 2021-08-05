package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.GetSwimReportsPort;
import com.swimbook.swimbook.domain.*;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetSwimReports implements GetSwimReportsPort {


    /**
     * Service: collects and returns all swim reports associated with a swim and a given duration
     * @param startDateTime YYYYMMDDhhmm
     * @param endDateTime YYYYMMDDhhmm
     * @param requiredModel (swim)
     * @return List containing all SwimReport instances for given Swim and duration.  Empty list is none found
     */
    @Override
    public List<SwimReport> execute(String startDateTime, String endDateTime, RequiredModel requiredModel) {
        //Angler theAngler = requiredModel.getAnglers().get(0);
        Swim theSwim = requiredModel.getSwims().get(0);

        List<Booking> theBookings = theSwim.getBookings();
        List<SwimReport> theReports = new ArrayList<>();
        Interval searchDuration = new Interval(CreateDateTimeFromString.stringToDateTime(startDateTime),
                CreateDateTimeFromString.stringToDateTime(endDateTime));

        for(Booking eachBooking : theBookings) {
            if(eachBooking.getBookingDuration().contains(searchDuration)) {
                theReports.addAll(eachBooking.getReports());
            }
        }

        return theReports;
    }
}
