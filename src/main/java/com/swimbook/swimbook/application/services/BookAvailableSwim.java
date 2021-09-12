package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.BookAvailableSwimPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.Angler;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.Swim;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swimbook.swimbook.application.services.CreateDateTimeFromString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookAvailableSwim implements BookAvailableSwimPort {

    private final CommitDomainModel commitDomainModel;

    /**
     * Constructor creates a new instance and autowires the dependency
     */
    @Autowired
    public BookAvailableSwim(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    /**
     * Creates a new Booking instance for the given Swim on the given startDateTime and endDateTime
     *
     * @precondition Swim is available when the given startDateTime and endDateTime
     * @postcondition A new Booking object is created and associated with Person and Swim
     * @param startDateTime must be in the format YYYYMMDDhhmm
     * @param endDateTime must be in the format YYYYMMDDhhmm
     * @param requiredModel (theSwim :Swim, theAngler :Angler, theSwim associated Bookings :Booking)
     * @return TRUE if successful
     */
    @Override
    public Boolean execute(String startDateTime,
                           String endDateTime,
                           RequiredModel requiredModel) throws IllegalArgumentException {

        //parse the strings into Interval instances using static utility class
        Interval requestedInterval = new Interval(CreateDateTimeFromString.stringToDateTime(startDateTime),
                CreateDateTimeFromString.stringToDateTime(endDateTime));

        //unpack the RequiredModel wrapper class into variables
        List<Booking> swimBookings = requiredModel.getBookings();
        Angler theAngler = requiredModel.getAnglers().get(0);
        Swim theSwim = requiredModel.getSwims().get(0);

        //add anglers bookings to ensure no double bookings
        swimBookings.addAll(theAngler.getBookings());

        //Redundancy check for swim and angler availability
        for(Booking each : swimBookings) {
            if(each.getBookingDuration().contains(requestedInterval)) {
                throw new IllegalArgumentException(
                        "Something went wrong. Check swim availability or anglers current bookings");
            }
        }

        //create the new booking instance
        Booking newBooking = new Booking(theSwim, theAngler, requestedInterval);
        theAngler.addBooking(newBooking);

        //create returnModel
        RequiredModel returnModel = this.createModelContainer(newBooking, theSwim, theAngler, requiredModel);

        this.commitDomainModel.commitDomainModel(returnModel);
        return Boolean.TRUE;
    }

    /**
     * Helper method for repacking domain model into container
     */
    private RequiredModel createModelContainer(Booking booking, Swim swim,
                                               Angler angler, RequiredModel returnModel) {

        //set up model container
        returnModel.empty();
        returnModel.setBookings(booking);
        returnModel.setSwims(swim);
        returnModel.setAnglers(angler);

        return returnModel;
    }
}
