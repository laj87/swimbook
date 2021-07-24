/**
 * This class is used to wrap the required com.swimbook.swimbook.domain classes as they are sent to the services provided
 * by the system.  It utilises multiple overloaded constructors in order to create the various groupings
 * required by the different services.  This class helps to decouple the RESTadapter from the other parts of
 * the system, and stops the necessity of passing com.swimbook.swimbook.domain objects as arguments.
 */

package com.swimbook.swimbook.adapter.in;

import com.swimbook.swimbook.domain.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//TODO this class needs to utilise the database - current iteration of class is for testing purposes

@Getter
@Setter
public class RequiredModel {

    private List<FishingVenue> fishingVenues;
    private List<Swim> swims;
    private List<Booking> bookings;
    private List<Status> statuses;
    private List<News> news;
    private List<Angler> anglers;
    private List<Bailiff> bailiffs;
    private List<SwimReport> swimReports;

    /**
     * No args constructor, creates empty RequiredModel instance
     */
    public RequiredModel(){}

    /**
     * Constructor used to create a RequiredModel containing a Venue
     * @param fishingVenue An arraylist of FishingVenues
     */
    public RequiredModel(List<FishingVenue> fishingVenue) {
        this.fishingVenues = fishingVenue;
    }

}
