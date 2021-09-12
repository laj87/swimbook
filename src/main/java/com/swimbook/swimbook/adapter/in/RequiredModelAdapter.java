package com.swimbook.swimbook.adapter.in;


import com.swimbook.swimbook.adapter.out.*;
import com.swimbook.swimbook.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.SecondaryTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    protected List<FishingVenue> findFishingVenueByVenueName(String name) {
        return fishingVenueRepo.findFishingVenueByVenueName(name);
    }

    /**
     * Creates a RequiredModel instance containing the required model for Available
     * Swims service
     * (FishingVenue, FishingVenue Swim instances, Swim Status and Booking instances)
     * @param name the name of the fishing venue
     * @return the required model wrapped into a class
     */
    protected RequiredModel getAvailableSwims(String name) {
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
    protected RequiredModel getBookSwimModel(int anglerID, int swimID) {
        //set up variables to hold the required entities
        List<Swim> swim = new ArrayList<>();
        List<Angler> angler = new ArrayList<>();

        //collect the entities from the repo and assign them to variables
        swim.add(this.swimRepo.findSwimBySwimID(swimID));
        angler.add(this.anglerRepo.findAnglerByMembershipID(anglerID));
        List<Booking> associatedBookings = swim.get(0).getBookings();

        //create and populate the RequiredModel wrapper class
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
    protected RequiredModel getSwimReportModel(int personID, int bookingID) {
        List<Angler> person = new ArrayList<>();
        List<Booking> booking = new ArrayList<>();

        person.add(this.anglerRepo.findAnglerByMembershipID(personID));
        booking.add(this.bookingRepo.findBookingByBookingID(bookingID));

        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setAnglers(person);
        requiredModel.setBookings(booking);

        return requiredModel;
    }

    /**
     * Creates RequiredModel container for addSwim use-case. NOTE - this is also currently being used for
     * setVenueAvailability and addNews
     * @param staffID
     * @param venueName
     * @return (Bailiff, FishingVenue)
     */
    protected RequiredModel getAddSwimsModel(int staffID, String venueName) {

        //TODO - move this to separate method
        //Check staffID is valid
        //List<Bailiff> bailiffs = this.bailiffRepo.findBailiffByStaffID(staffID);
        //if(bailiffs.isEmpty()) {
        //    throw new IllegalArgumentException("no staff identified by that ID");
        //}

        List<FishingVenue> venues = this.fishingVenueRepo.findFishingVenueByVenueName(venueName);
        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setFishingVenues(venues);
        return requiredModel;
    }

    /**
     *
     * @param staffID
     * @param swimID
     * @return (swim)
     */
    protected RequiredModel getSetSwimAvailabilityModel(int staffID, int swimID) {

        //Check staffID is valid
        //List<Bailiff> bailiffs = this.bailiffRepo.findBailiffByStaffID(staffID);
        //if(bailiffs.isEmpty()) {
        //    throw new IllegalArgumentException("no staff identified by that ID");
        //}

        Swim swim = this.swimRepo.findSwimBySwimID(swimID);
        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setSwims(swim);

        return requiredModel;
    }

    /**
     * Creates and returns a RequiredModel instance containing the model for the block booking service
     * @param staffID staffID for the bailiff making the booking
     * @param swimIDs ID codes for the swims to be booked
     * @return RequiredModel instance containing (Bailiff, Swims)
     */
    protected RequiredModel getBlockSwimBookerModel(int staffID, List<Integer> swimIDs) {
        //Check staffID is valid
        List<Bailiff> bailiffs = this.bailiffRepo.findBailiffByStaffID(staffID);
        if(bailiffs.isEmpty()) {
            throw new IllegalArgumentException("no staff identified by that ID");
        }

        List<Swim> swims = new ArrayList<>();
        for(Integer eachID : swimIDs) {
            swims.add(this.swimRepo.findSwimBySwimID(eachID));
        }

        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setBailiffs(bailiffs);
        requiredModel.setSwims(swims);

        return requiredModel;
    }


    /**
     * packages the required model needed for get swim reports service
     * @param memberID ID of the user making the request
     * @param swimID ID of the swim
     * @return RequiredModel container (Swim, Angler)
     */
    protected RequiredModel getSwimReportsModel(int memberID, int swimID) {
        Angler user = this.anglerRepo.findAnglerByMembershipID(memberID);
        Swim swim = this.swimRepo.findSwimBySwimID(swimID);
        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setSwims(swim);
        requiredModel.setAnglers(user);
        return requiredModel;
    }

    protected RequiredModel  getInstantiateModel(int memberID) {

        //if(this.anglerRepo.findAnglerByMembershipID(memberID) == null) {
        //    throw new IllegalArgumentException("membership number is unknown");
        //}
        List<FishingVenue> venues = this.fishingVenueRepo.findAll();

        RequiredModel requiredModel = new RequiredModel();
        requiredModel.setFishingVenues(venues);
        return requiredModel;
    }

}


