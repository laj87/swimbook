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

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param fishingVenue
     */
    public void setFishingVenues(FishingVenue fishingVenue) {
        List<FishingVenue> container = new ArrayList<>();
        container.add(fishingVenue);
        this.fishingVenues = container;
    }

    public void setFishingVenues(List<FishingVenue> fishingVenues) {
        this.fishingVenues = fishingVenues;
    }


    /**
     * Creates a container collection to store the FishingVenue instance
     * @param swim
     *
     */
    public void setSwims(Swim swim) {
        List<Swim> container = new ArrayList<>();
        container.add(swim);
        this.swims = container;
    }

    public void setSwims(List<Swim> swims) {
        this.swims = swims;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Creates a container collection to store the Booking instance
     * @param booking
     *
     */
    public void setBookings(Booking booking) {
        List<Booking> container = new ArrayList<>();
        container.add(booking);
        this.bookings = container;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param status
     *
     */
    public void setStatuses(Status status) {
        List<Status> container = new ArrayList<>();
        container.add(status);
        this.statuses = container;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param news
     *
     */
    public void setNews(News news) {
        List<News> container = new ArrayList<>();
        container.add(news);
        this.news = container;
    }

    public void setAnglers(List<Angler> anglers) {
        this.anglers = anglers;
    }

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param angler
     *
     */
    public void setAnglers(Angler angler) {
        List<Angler> container = new ArrayList<>();
        container.add(angler);
        this.anglers = container;
    }

    public void setBailiffs(List<Bailiff> bailiffs) {
        this.bailiffs = bailiffs;
    }

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param bailiff
     *
     */
    public void setBailiffs(Bailiff bailiff) {
        List<Bailiff> container = new ArrayList<>();
        container.add(bailiff);
        this.bailiffs = container;
    }

    public void setSwimReports(List<SwimReport> swimReports) {
        this.swimReports = swimReports;
    }

    /**
     * Creates a container collection to store the FishingVenue instance
     * @param swimReport
     *
     */
    public void setSwimReports(SwimReport swimReport) {
        List<SwimReport> container = new ArrayList<>();
        container.add(swimReport);
        this.swimReports = container;
    }

    public void empty(){
        this.fishingVenues = null;
        this.swims = null;
        this.bookings = null;
        this.statuses = null;
        this.news = null;
        this.anglers = null;
        this.bailiffs = null;
        this.swimReports = null;
    }
}
