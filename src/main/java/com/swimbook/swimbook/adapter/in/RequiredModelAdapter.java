package com.swimbook.swimbook.adapter.in;


import com.swimbook.swimbook.adapter.out.*;
import com.swimbook.swimbook.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequiredModelAdapter {

    private final SpringDataFishingVenueRepo fishingVenueRepo;
    private final SpringDataSwimRepo swimRepo;
    private final SpringDataAnglerRepo anglerRepo;
    private final SpringDataBookingRepo bookingRepo;
    private final SpringDataBailiffRepo bailiffRepo;
    private final SpringDataNewsRepo newsRepo;
    private final SpringDataStatusRepo statusRepo;
    private final SpringDataSwimReportRepo swimReportRepo;

    @Autowired
    public RequiredModelAdapter(SpringDataFishingVenueRepo fishingVenueRepo,
                                SpringDataSwimRepo swimRepo,
                                SpringDataAnglerRepo anglerRepo,
                                SpringDataBookingRepo bookingRepo,
                                SpringDataBailiffRepo bailiffRepo,
                                SpringDataNewsRepo newsRepo,
                                SpringDataStatusRepo statusRepo,
                                SpringDataSwimReportRepo swimReportRepo) {

        this.fishingVenueRepo = fishingVenueRepo;
        this.swimRepo = swimRepo;
        this.anglerRepo = anglerRepo;
        this.bookingRepo = bookingRepo;
        this.bailiffRepo = bailiffRepo;
        this.newsRepo = newsRepo;
        this.statusRepo = statusRepo;
        this.swimReportRepo = swimReportRepo;
    }

    /**
     * Finds an individual Venue based on the VenueName attribute
     * @return
     */
    public List<FishingVenue> findFishingVenueByVenueName(String name) {
        return fishingVenueRepo.findFishingVenueByVenueName(name);
    }

    /**
     * Creates a RequiredModel instance containing the required model for Available
     * Swims service
     * (FishingVenue, FishingVenue Swim instances, Swim Status and Booking instances)
     * @param name the name of the fishing venue
     * @return the required model wrapped into a class
     */
    public RequiredModel getAvailableSwims(String name) {
        List<FishingVenue> venues = this.findFishingVenueByVenueName(name);
        List<Swim> swims = venues.get(0).getSwims();
        List<Status> statuses = new ArrayList<>();
        List<List> swimBookings = new ArrayList<>();

        for(Swim each : swims) {
            statuses.add(each.getSwimStatus());
            swimBookings.add(each.getBookings());
        }

        //unpack the booking collection - parameter is provided as a list of lists
        List<Booking> bookings = new ArrayList<Booking>();
        for(List each : swimBookings) {
            for(Object i : each) {
                bookings.add((Booking)i);
            }
        }

        RequiredModel requiredModel = new RequiredModel();

        requiredModel.setFishingVenues(venues);
        requiredModel.setSwims(swims);
        requiredModel.setStatuses(statuses);
        requiredModel.setBookings(bookings);

        return requiredModel;
    }

    /**
     * Creates a RequiredModel instance containing the model required for the Book
     * Swim Service
     * (anglerA :Angler, Swim, anglerA associated Booking instances)
     * @precondition Swim is available for the given duration
     * @param anglerID ID of the angler wishing to make the booking
     * @param swimID ID of the swim the angler wishes to book
     * @return
     */
    public RequiredModel getBookSwimModel(int anglerID, int swimID) {
        List<Swim> swim = new ArrayList<>();
        List<Angler> angler = new ArrayList<>();
        swim.add(this.swimRepo.findSwimBySwimID(swimID));
        angler.add(this.anglerRepo.findAnglerByMembershipID(anglerID));
        List<Booking> associatedBookings = swim.get(0).getBookings();

        RequiredModel requiredModel = new RequiredModel();

        requiredModel.setAnglers(angler);
        requiredModel.setSwims(swim);
        requiredModel.setBookings(associatedBookings);

        return requiredModel;

    }

    /**
     * Creates an instance of RequiredModel that contains the model required for
     * the Add Swim Report Service
     * (anglerA :Angler, bookingA :Booking)
     * @param personID
     * @param bookingID
     * @return
     */
    public RequiredModel getSwimReportModel(int personID, int bookingID) {
        List<Angler> person = new ArrayList<>();
        List<Booking> booking = new ArrayList<>();

        person.add(this.anglerRepo.findAnglerByMembershipID(personID));
        booking.add(this.bookingRepo.findBookingByBookingID(bookingID));

        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setAnglers(person);
        requiredModel.setBookings(booking);

        return requiredModel;
    }

}
