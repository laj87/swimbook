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
     * @precondition Swims are available when the given startDateTime and endDateTime
     * @postcondition A new Booking object is created and associated with Person and Swim
     * @param startDateTime must be in the format YYYYMMDDhhmm
     * @param endDateTime must be in the format YYYYMMDDhhmm
     * @param requiredModel (theSwim :Swim, theAngler :Angler, theSwim associated Bookings :Booking)
     * @return TRUE if successful
     */
    public Boolean execute(String startDateTime,
                           String endDateTime,
                           RequiredModel requiredModel) throws IllegalArgumentException {

        Interval requestedInterval = new Interval(createDateTimeInstance(startDateTime),
                createDateTimeInstance(endDateTime));

        List<Booking> swimBookings = requiredModel.getBookings();
        Angler theAngler = requiredModel.getAnglers().get(0);
        Swim theSwim = requiredModel.getSwims().get(0);

        //add anglers bookings to ensure no double bookings
        swimBookings.addAll(theAngler.getBookings());

        //Redundancy check for swim availability
        for(Booking each : swimBookings) {
            if(each.getBookingDuration().contains(requestedInterval)) {
                throw new IllegalArgumentException(
                        "Something went wrong. Check swim availability or anglers current bookings");
            }
        }

        Booking newBooking = new Booking(theSwim, theAngler, requestedInterval);
        theSwim.addBooking(newBooking);
        theAngler.addBooking(newBooking);

        //create returnModel
        RequiredModel returnModel = this.createModelContainer(newBooking, theSwim, theAngler);

        this.commitDomainModel.commitDomainModel(returnModel);
        return Boolean.TRUE;

    }

    /**
     * Helper Method
     * creates and returns a new datetime instance using a string as input
     * @param dateTime must be in the format YYYYMMDDHHmm
     * @return DateTime instance representing the string input by the user
     */
    private DateTime createDateTimeInstance(String dateTime) {
        char[] dateTimeTokens = dateTime.toCharArray();
        int YYYY = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,0,3).toString());
        int MM = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,4,5).toString());
        int DD = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,6,7).toString());
        int HH = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,8,9).toString());
        int mm = Integer.parseInt(Arrays.copyOfRange(dateTimeTokens,10,11).toString());

        return new DateTime(YYYY,MM,DD,HH,mm);
    }

    /**
     * Helper method for repacking domain model into container
     */
    private RequiredModel createModelContainer(Booking booking, Swim swim, Angler angler) {
        //Set up commit fixture - consider helper method

        ArrayList<Booking> returnBooking = new ArrayList<>();
        ArrayList<Swim> returnSwim = new ArrayList<>();
        ArrayList<Angler> returnAngler = new ArrayList<>();

        //add domain model
        returnBooking.add(booking);
        returnSwim.add(swim);
        returnAngler.add(angler);

        //set up model container
        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setBookings(returnBooking);
        returnModel.setSwims(returnSwim);
        returnModel.setAnglers(returnAngler);

        return returnModel;
    }
}
